package com.example.demo.server.handler;

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
public class LoginRequestHandler1 extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket packet) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(packet.getVersion());
        loginResponsePacket.setUserName(packet.getUsername());

        if (valid(packet)){
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + packet.getUsername() + "]登录成功");
            SessionUtil.bindSession(new Session(userId, packet.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        return "lsk".equals(loginRequestPacket.getPassword());
    }

    private static String randomUserId(){
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
