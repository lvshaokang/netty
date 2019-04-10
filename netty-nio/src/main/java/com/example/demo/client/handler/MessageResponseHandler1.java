package com.example.demo.client.handler;

import com.example.demo.protocol.response.MessageResponsePacket;
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
public class MessageResponseHandler1 extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket packet) throws Exception {
        String fromUserId = packet.getFromUserId();
        String fromUserName = packet.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + packet .getMessage());

    }
}
