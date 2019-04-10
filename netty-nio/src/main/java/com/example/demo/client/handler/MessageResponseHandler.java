package com.example.demo.client.handler;

import com.example.demo.protocol.request.MessageRequestPacket;
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
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket packet) throws Exception {
        System.out.println(new Date() + ": 收到服务端的消息: " + packet.getMessage());
    }
}
