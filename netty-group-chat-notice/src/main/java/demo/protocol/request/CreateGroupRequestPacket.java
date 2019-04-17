package demo.protocol.request;

import demo.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

import static demo.protocol.command.Command.CREATE_GROUP_REQUEST;

/**
 * TODO
 *
 * @author lsk
 * @class_name CreateGroupRequestPacket
 * @date 2019-04-11
 */
@Data
@EqualsAndHashCode(callSuper =true)
@ToString(callSuper =true)
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
