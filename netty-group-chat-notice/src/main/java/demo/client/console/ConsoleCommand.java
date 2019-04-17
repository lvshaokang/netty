package demo.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * TODO
 *
 * @author lsk
 * @class_name ConsoleCommand
 * @date 2019-04-11
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
