package com.example.demo.protocol.request;

import com.example.demo.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.demo.protocol.command.Command.MESSAGE_REQUEST;

/**
 * TODO
 *
 * @author lsk
 * @class_name MessageRequestPacket
 * @date 2019/2/12
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    public MessageRequestPacket(String toUserId,String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
