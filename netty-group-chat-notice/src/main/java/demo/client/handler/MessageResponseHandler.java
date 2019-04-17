package demo.client.handler;

import demo.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * TODO
 *
 * @author lsk
 * @class_name MessageRequestHandler
 * @date 2019/2/15
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket packet) throws Exception {
        String fromUserId = packet.getFromUserId();
        String fromUserName = packet.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + packet
                .getMessage());
    }
}
