package demo.protocol.request;

import demo.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static demo.protocol.command.Command.LOGOUT_REQUEST;

/**
 * TODO
 *
 * @author lsk
 * @class_name LogoutRequestPacket
 * @date 2019-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
