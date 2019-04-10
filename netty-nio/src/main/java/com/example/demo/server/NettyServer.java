package com.example.demo.server;

import com.example.demo.codec.PacketDecoder;
import com.example.demo.codec.PacketEncoder;
import com.example.demo.server.handler.*;
import com.example.demo.utils.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * TODO
 *
 * @author lsk
 * @class_name NettyServer
 * @date 2019/1/30
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 接收新连接线程,主要负责创建新连接
        NioEventLoopGroup boos = new NioEventLoopGroup();
        // 读取数据的线程,主要用于读取数据以及业务逻辑处理
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.
                group(boos,worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                // 用于指定在服务端启动过程中的一些逻辑
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println("服务端启动中....");
                    }
                })
                // 用于指定处理新连接数据的读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel channel){
//                        channel.pipeline().addLast(new FirstServerHandler());
//                        channel.pipeline().addLast(new ServerHandler());
//                        channel.pipeline().addLast(new InBoundHandlerA());
//                        channel.pipeline().addLast(new InBoundHandlerB());
//                        channel.pipeline().addLast(new InBoundHandlerC());

//                        channel.pipeline().addLast(new OutBoundHandlerA());
//                        channel.pipeline().addLast(new OutBoundHandlerB());
//                        channel.pipeline().addLast(new OutBoundHandlerC());
//                        channel.pipeline().addLast(new LifeCycleTestHandler());
                        channel.pipeline().addLast(new Spliter());
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new LoginRequestHandler1());
                        // 用户认证
                        channel.pipeline().addLast(new AuthHandler());
                        channel.pipeline().addLast(new MessageRequestHandler1());
                        channel.pipeline().addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap,8000);

    }

    private static void bind(ServerBootstrap serverBootstrap, int port){
        serverBootstrap.bind(8000).addListener(future -> {
            if (future.isSuccess()){
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap,port + 1);
            }
        });
    }
}
