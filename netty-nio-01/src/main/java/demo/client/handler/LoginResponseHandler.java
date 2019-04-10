package demo.client.handler;

import demo.protocol.request.LoginRequestPacket;
import demo.protocol.response.LoginResponsePacket;
import demo.utils.LoginUtil;
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
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("lsk");
        loginRequestPacket.setPassword("lsk");

        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
       if (packet.isSuccess()){
           System.out.println(new Date() + ": 客户端登录成功");
           LoginUtil.markAsLogin(ctx.channel());
       } else {
           System.out.println(new Date() + ": 客户端登录失败，原因：" + packet.getReason());
       }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭!");
    }
}
