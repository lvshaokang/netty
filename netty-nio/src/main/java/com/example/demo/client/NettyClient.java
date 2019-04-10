package com.example.demo.client;

import com.example.demo.client.handler.*;
import com.example.demo.codec.PacketDecoder;
import com.example.demo.codec.PacketEncoder;
import com.example.demo.protocol.PacketCodeC;
import com.example.demo.protocol.request.LoginRequestPacket;
import com.example.demo.protocol.request.MessageRequestPacket;
import com.example.demo.server.handler.LifeCycleTestHandler;
import com.example.demo.utils.LoginUtil;
import com.example.demo.utils.SessionUtil;
import com.example.demo.utils.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author lsk
 * @class_name NettyClient
 * @date 2019/1/30
 */
public class NettyClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                // 指定线程模型
                .group(group)
                // 指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // IO 处理器
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel  channel) throws Exception {
//                        channel.pipeline().addLast(new FirstClientHandler());
//                        channel.pipeline().addLast(new ClientHandler());
                        // 测试channelHandler 的生命周期
//                        channel.pipeline().addLast(new LifeCycleTestHandler());
                        // 拆包器
                        channel.pipeline().addLast(new Spliter());
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new LoginResponseHandler1());
                        channel.pipeline().addLast(new MessageResponseHandler1());
                        channel.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap,"127.0.0.1",8000,MAX_RETRY);

        /*
        Channel channel = bootstrap.connect("127.0.0.1",8000).channel();

        while (true){
            channel.writeAndFlush(new Date() + ": hello world");
            Thread.sleep(2000);
        }*/


    }


    private static void connect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect("127.0.0.1",8000).addListener(future -> {
            if (future.isSuccess()){
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture)future).channel();

                startConsoleThread1(channel);
            } else if (retry == 0){
                System.out.println("重试次数已用完，放弃连接!");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;

                // 本次重连的间隔
                int delay = 1 << order;
                System.out.println("重连间隔: " + delay);
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() ->  connect(bootstrap,host,port,retry - 1),delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel){
        new Thread(() -> {
           while (!Thread.interrupted()){
//               if (LoginUtil.hasLogin(channel)){
                   System.out.println("输入消息发送至服务端: ");
                   Scanner sc = new Scanner(System.in);
                   String line = sc.nextLine();

//                   MessageRequestPacket packet = new MessageRequestPacket();
//                   packet.setMessage(line);
//                   ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(),packet);
                   channel.writeAndFlush(new MessageRequestPacket(line));
//               }
           }
        }).start();
    }

    private static void startConsoleThread1(Channel channel){
        Scanner sc = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        new Thread(() -> {
            while (!Thread.interrupted()){
               if (!SessionUtil.hasLogin(channel)){
                   System.out.print("输入用户名登录: ");
                   String username = sc.nextLine();
                   loginRequestPacket.setUsername(username);

                   // 密码使用默认的
                   loginRequestPacket.setPassword("lsk");

                channel.writeAndFlush(loginRequestPacket);
                waitForLoginResponse();
               } else {
                   String toUserId = sc.next();
                   String message = sc.next();
                   channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
               }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
