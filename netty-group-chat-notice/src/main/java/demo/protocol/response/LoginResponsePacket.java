package demo.protocol.response;

import demo.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static demo.protocol.command.Command.LOGIN_RESPONSE;


/**
 * TODO
 *
 * @author lsk
 * @class_name LoginRequestPacket
 * @date 2019/2/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
