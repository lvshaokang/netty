package com.example.demo.protocol.request;

import com.example.demo.protocol.Packet;
import lombok.Data;

import static com.example.demo.protocol.command.Command.MESSAGE_REQUEST;

/**
 * TODO
 *
 * @author lsk
 * @class_name MessageRequestPacket
 * @date 2019/2/12
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
