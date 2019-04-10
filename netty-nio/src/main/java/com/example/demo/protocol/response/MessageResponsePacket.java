package com.example.demo.protocol.response;

import com.example.demo.protocol.Packet;
import lombok.Data;

import static com.example.demo.protocol.command.Command.MESSAGE_REQUEST;
import static com.example.demo.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * TODO
 *
 * @author lsk
 * @class_name MessageRequestPacket
 * @date 2019/2/12
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    private String fromUserId;

    private String fromUserName;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
