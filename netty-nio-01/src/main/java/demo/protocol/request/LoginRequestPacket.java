package demo.protocol.request;

import demo.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static demo.protocol.command.Command.LOGIN_REQUEST;

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
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
