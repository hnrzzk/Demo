//package priv.demo.net.mina;
//
//import org.apache.log4j.Logger;
//import org.apache.mina.core.service.IoConnector;
//import org.apache.mina.core.service.IoHandlerAdapter;
//import org.apache.mina.core.session.IoSession;
//import org.apache.mina.filter.codec.ProtocolCodecFilter;
//import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
//import org.apache.mina.transport.socket.nio.NioSocketConnector;
//
//import java.net.InetSocketAddress;
//import java.nio.charset.Charset;
//
///**
// * Created by zhangkai on 2018/6/14.
// */
//public class MinaTcpClient {
//    public static void main(String[] argvs)
//    {
//        IoConnector connector = new NioSocketConnector();
//        connector.setConnectTimeoutMillis(30000);
//        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
//        connector.setHandler(new TCPClientHandler("hello world"));
//        connector.connect(new InetSocketAddress("localhost", 9123));
//    }
//}
//
//class TCPClientHandler extends IoHandlerAdapter
//{
//    private final static Logger log = Logger.getLogger(TCPClientHandler.class);
//    private String values;
//
//    public TCPClientHandler(String values)
//    {
//        this.values = values;
//    }
//
//    public void sessionOpened(IoSession session)
//    {
//        session.write(values);
//    }
//}
