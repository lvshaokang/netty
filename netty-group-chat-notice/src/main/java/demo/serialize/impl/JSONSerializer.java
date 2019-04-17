package demo.serialize.impl;


import com.alibaba.fastjson.JSON;
import demo.serialize.Serializer;
import demo.serialize.SerializerAlgorithm;

/**
 * TODO
 *
 * @author lsk
 * @class_name JSONSerializer
 * @date 2019/2/12
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
