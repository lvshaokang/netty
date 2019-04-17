package demo.protocol.response;

import demo.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static demo.protocol.command.Command.MESSAGE_RESPONSE;


/**
 * TODO
 *
 * @author lsk
 * @class_name MessageRequestPacket
 * @date 2019/2/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
