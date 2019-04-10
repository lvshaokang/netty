package demo.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * TODO
 *
 * @author lsk
 * @class_name Packet
 * @date 2019/2/12
 */
@Data
public abstract class Packet {

    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
