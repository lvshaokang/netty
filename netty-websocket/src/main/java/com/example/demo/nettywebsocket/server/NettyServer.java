package com.example.demo.nettywebsocket.server;

import com.example.demo.nettywebsocket.handler.WebSocketChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


/**
 * TODO
 *
 * @author lsk
 * @class_name NettyServer
 * @date 2019-04-17
 */
@Slf4j
@Component
public class NettyServer {

    private static final int PORT = 8081;

    @PostConstruct
    public void init(){
        new Thread(() -> startServer()).start();
    }

    private void startServer(){
        NioEventLoopGroup boos = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap
                    .group(boos,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<NioServerSocketChannel> () {

                        @Override
                        protected void initChannel(NioServerSocketChannel channel) throws Exception {
                            log.debug("服务端启动中...");
                        }
                    })
                    .childHandler(new ChannelInitializer<NioSocketChannel> () {

                        @Override
                        protected void initChannel(NioSocketChannel channel) throws Exception {
                            log.debug("收到新连接...");

                            channel.pipeline().addLast(new HttpServerCodec());
                            channel.pipeline().addLast(new HttpObjectAggregator(65536));
                            channel.pipeline().addLast(new ChunkedWriteHandler());
//                            channel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
                            channel.pipeline().addLast(new IdleStateHandler(60,30,60*30, TimeUnit.SECONDS));

                            channel.pipeline().addLast(new WebSocketChannelHandler());
                        }
                    });

            Channel channel = bind(serverBootstrap, PORT);

            channel.closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private static Channel bind(ServerBootstrap serverBootstrap, int port) throws InterruptedException {
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });

        return channelFuture.channel();
    }
}
