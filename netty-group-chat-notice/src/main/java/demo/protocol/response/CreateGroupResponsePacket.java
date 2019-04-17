package demo.protocol.response;

import demo.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

import static demo.protocol.command.Command.CREATE_GROUP_RESPONSE;

/**
 * TODO
 *
 * @author lsk
 * @class_name CreateGroupResponsePacket
 * @date 2019-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
