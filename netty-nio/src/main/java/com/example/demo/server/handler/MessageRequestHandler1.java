package com.example.demo.server.handler;

import com.example.demo.protocol.request.MessageRequestPacket;
import com.example.demo.protocol.response.MessageResponsePacket;
import com.example.demo.session.Session;
import com.example.demo.utils.SessionUtil;
import io.netty.channel.Channel;
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
public class MessageRequestHandler1 extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket packet) throws Exception {

        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();

        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageResponsePacket.getMessage());

        Channel toUserChannel = SessionUtil.getChannel(packet.getToUserId());

        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + packet.getToUserId() + "] 不在线，发送失败!");
        }
    }
}
