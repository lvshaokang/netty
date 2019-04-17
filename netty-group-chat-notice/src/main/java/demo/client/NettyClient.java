package demo.client;

import demo.client.console.ConsoleCommandManager;
import demo.client.console.LoginConsoleCommand;
import demo.client.handler.CreateGroupResponseHandler;
import demo.client.handler.LoginResponseHandler;
import demo.client.handler.LogoutResponseHandler;
import demo.client.handler.MessageResponseHandler;
import demo.codec.PacketDecoder;
import demo.codec.PacketEncoder;
import demo.codec.Spliter;
import demo.protocol.request.LoginRequestPacket;
import demo.protocol.request.MessageRequestPacket;
import demo.server.handler.CreateGroupRequestHandler;
import demo.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
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
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

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
                        // 拆包器
                        channel.pipeline().addLast(new Spliter());
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new LoginResponseHandler());
                        channel.pipeline().addLast(new LogoutResponseHandler());
                        channel.pipeline().addLast(new MessageResponseHandler());
                        channel.pipeline().addLast(new CreateGroupResponseHandler());
                        channel.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap,HOST,PORT,MAX_RETRY);
    }


    private static void connect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect(host,port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture)future).channel();

                startConsoleThread(channel);
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

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner,channel);
                } else {
                    consoleCommandManager.exec(scanner,channel);
                }
            }
        }).start();
    }



}
