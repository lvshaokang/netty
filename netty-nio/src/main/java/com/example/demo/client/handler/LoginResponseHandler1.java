package com.example.demo.client.handler;

import com.example.demo.protocol.request.LoginRequestPacket;
import com.example.demo.protocol.response.LoginResponsePacket;
import com.example.demo.session.Session;
import com.example.demo.utils.LoginUtil;
import com.example.demo.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * TODO
 *
 * @author lsk
 * @class_name LoginRequestHandler
 * @date 2019/2/15
 */
public class LoginResponseHandler1 extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
        String userId = packet.getUserId();
        String userName = packet.getUserName();

        if (packet.isSuccess()) {
            System.out.println("[" + userName + "]登录成功，userId 为: " + packet.getUserId());
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println("[" + userName + "]登录失败，原因：" + packet.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭!");
    }
}
