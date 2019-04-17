package com.example.demo.nettywebsocket.util;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * TODO
 *
 * @author lsk
 * @class_name ChannelUtil
 * @date 2019-04-17
 */
public class ChannelUtil {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
