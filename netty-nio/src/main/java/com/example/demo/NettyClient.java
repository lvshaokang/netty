package com.example.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;
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
                // IO 处理器
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new FirstClientHandler());
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
                System.out.println("连接成功!");
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
}
