package demo.server.handler;

import demo.protocol.request.MessageRequestPacket;
import demo.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * TODO
 *
 * @author lsk
 * @class_name MessageRequestHandler
 * @date 2019/2/15
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket packet) throws Exception {

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();

        System.out.println(new Date() + ": 收到客户端消息: " + packet.getMessage());

        messageResponsePacket.setMessage("服务端回复【" + packet.getMessage() + "】");


        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
