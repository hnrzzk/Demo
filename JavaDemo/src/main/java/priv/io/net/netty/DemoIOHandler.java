package priv.io.net.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DemoIOHandler extends SimpleChannelInboundHandler<Integer> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Integer integer) throws Exception {
        System.out.println("receive data:" + integer);
    }
}
