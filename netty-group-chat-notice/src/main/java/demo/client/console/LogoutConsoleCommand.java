package demo.client.console;

import demo.protocol.request.LoginRequestPacket;
import demo.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * TODO
 *
 * @author lsk
 * @class_name LogoutConsoleCommand
 * @date 2019-04-11
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket loginRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(loginRequestPacket);
    }
}
