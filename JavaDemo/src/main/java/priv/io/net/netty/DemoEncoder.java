package priv.io.net.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DemoEncoder extends MessageToByteEncoder<DemoMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DemoMessage s, ByteBuf byteBuf) throws Exception {
        Thread.sleep(1000);
        byteBuf.writeInt(s.int_a);
    }
}
