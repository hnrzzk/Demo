package priv.io.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by zhangkai on 2018/4/26.
 */
public class Client {
    public void start() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        System.out.println(socketChannel);
    }
}
