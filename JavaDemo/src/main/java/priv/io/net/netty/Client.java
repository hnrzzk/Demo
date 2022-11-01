package priv.io.net.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public Channel channel = null;
    private NioEventLoopGroup nioEventLoopGroup = null;

    public void start() {
        nioEventLoopGroup = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast("decoder", new DemoDecoder())
                                .addLast("encoder", new DemoEncoder());
                    }
                });
        ChannelFuture chf = bootstrap.connect("127.0.0.1", 20000);
        try {
            chf.await();
            if (chf.isSuccess()) {
                System.out.println("连接成功");
                channel = chf.channel();
            }else {
                System.err.println("连接失败");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        channel.close();
        nioEventLoopGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        DemoMessage message = new DemoMessage();
        client.channel.writeAndFlush(message);
        message.int_a = 10;
        client.close();
        System.out.println("连接关闭");

    }
}
