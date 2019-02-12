package com.example.demo.protocol.request;

import com.example.demo.protocol.Packet;
import lombok.Data;

import static com.example.demo.protocol.command.Command.LOGIN_REQUEST;

/**
 * TODO
 *
 * @author lsk
 * @class_name LoginRequestPacket
 * @date 2019/2/12
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
