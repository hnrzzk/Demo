//package priv.demo.net.mina;
//
//import org.apache.log4j.Logger;
//import org.apache.mina.core.service.IoAcceptor;
//import org.apache.mina.core.session.IdleStatus;
//import org.apache.mina.example.gettingstarted.timeserver.TimeServerHandler;
//import org.apache.mina.filter.codec.ProtocolCodecFilter;
//import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
//import org.apache.mina.filter.logging.LoggingFilter;
//import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
//
//import java.net.InetSocketAddress;
//import java.nio.charset.Charset;
//
//
///**
// * Created by zhangkai on 2018/6/12.
// */
//public class MinaTimeServer {
//
//    private static Logger logger = Logger.getLogger(MinaTimeServer.class);
//    private static final int PORT = 9123;
//
//    public static void main(String[] args)
//    {
//        IoAcceptor acceptor = new NioSocketAcceptor();
//        try {
//            acceptor.getFilterChain().addLast("logger", new LoggingFilter());
//            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
//            acceptor.setHandler(new TimeServerHandler());
//            acceptor.getSessionConfig().setReadBufferSize(2048);    //session的缓冲去大小
//            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);  //session的空闲检测
//
//            acceptor.bind(new InetSocketAddress(PORT));
//        } catch (Exception e) {
//            logger.error("IoAcceptor bind address error:", e);
//        }
//    }
//}
//
