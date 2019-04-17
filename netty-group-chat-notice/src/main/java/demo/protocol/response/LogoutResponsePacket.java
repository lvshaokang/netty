package demo.protocol.response;

import demo.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static demo.protocol.command.Command.LOGOUT_RESPONSE;

/**
 * TODO
 *
 * @author lsk
 * @class_name LogoutResponsePacket
 * @date 2019-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
