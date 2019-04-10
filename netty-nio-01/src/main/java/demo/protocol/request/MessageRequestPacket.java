package demo.protocol.request;

import demo.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static demo.protocol.command.Command.MESSAGE_REQUEST;


/**
 * TODO
 *
 * @author lsk
 * @class_name MessageRequestPacket
 * @date 2019/2/12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessageRequestPacket extends Packet {

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
