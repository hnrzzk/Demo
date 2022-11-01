package priv.io.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhangkai on 2018/4/26.
 */
public class Server implements Runnable{
    ServerSocketChannel serverSocketChannel;
    Selector selector;
    volatile boolean stop = true;
    public Server(String hostName, int port){

    }

    public void init() throws IOException {
        //创建多路复用器selector，工厂方法
        this.selector = Selector.open();
        //创建ServerSocketChannel，工厂方法
        this.serverSocketChannel = ServerSocketChannel.open();
        //绑定ip和端口号，默认的IP=127.0.0.1，对连接的请求最大队列长度设置为backlog=1024，如果队列满时收到连接请求，则拒绝连接
        this.serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 9999), 1024);
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() {
        stop = false;
        while(!stop){
            try {
                //selector.select()会一直阻塞到有一个通道在你注册的事件上就绪了
                //selector.select(1000)会阻塞到1s后然后接着执行，相当于1s轮询检查
                this.selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while(it.hasNext()) {
                    key = it.next();
                    /**
                     * Selector不会自己从已选择键集中移除SelectionKey实例，必须在处理完通道时手动移除。
                     * 下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
                     */
                    it.remove();
                    try {
                        //处理准备就绪的key
                        handle(key);
                    } catch (Exception e) {
                        if (key != null) {
                            //请求取消此键的通道到其选择器的注册
                            key.cancel();
                            //关闭这个通道
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void handle(SelectionKey key) throws IOException {
        //如果key是有效的
        if(key.isValid()){
            //监听到有新客户端的接入请求
            //完成TCP的三次握手，建立物理链路层
            if(key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = (SocketChannel) ssc.accept();
                //设置客户端链路为非阻塞模式
                sc.configureBlocking(false);
                //将新接入的客户端注册到多路复用器Selector上
                sc.register(selector, SelectionKey.OP_READ);
            }
            //监听到客户端的读请求
            if(key.isReadable()){
                //获得通道对象
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //从channel读数据到缓冲区
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0){
                    //Flips this buffer.  The limit is set to the current position and then
                    // the position is set to zero，就是表示要从起始位置开始读取数据
                    readBuffer.flip();
                    //eturns the number of elements between the current position and the  limit.
                    // 要读取的字节长度
                    byte[] bytes = new byte[readBuffer.remaining()];
                    //将缓冲区的数据读到bytes数组
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("the time server receive order: " + body);
                    String currenttime = "query time order".equals(body) ? new Date(System.currentTimeMillis()).toString(): "bad order";
                    doWrite(sc, currenttime);
                }else if(readBytes < 0){
                    key.channel();
                    sc.close();
                }
            }
        }
    }

    public static void doWrite(SocketChannel channel, String response) throws IOException {
        if(!(null == response || response.isEmpty())){
            byte []  bytes = response.getBytes();
            //分配一个bytes的length长度的ByteBuffer
            ByteBuffer  write = ByteBuffer.allocate(bytes.length);
            //将返回数据写入缓冲区
            write.put(bytes);
            write.flip();
            //将缓冲数据写入渠道，返回给客户端
            channel.write(write);
        }
    }

}
