package com.example.demo.protocol.response;

import com.example.demo.protocol.Packet;
import lombok.Data;

import static com.example.demo.protocol.command.Command.LOGIN_REQUEST;
import static com.example.demo.protocol.command.Command.LOGIN_RESPONSE;

/**
 * TODO
 *
 * @author lsk
 * @class_name LoginRequestPacket
 * @date 2019/2/12
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
