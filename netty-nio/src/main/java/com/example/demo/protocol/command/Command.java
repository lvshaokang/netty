package com.example.demo.protocol.command;

/**
 * TODO
 *
 * @author lsk
 * @interface_name Command
 * @date 2019/2/12
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}