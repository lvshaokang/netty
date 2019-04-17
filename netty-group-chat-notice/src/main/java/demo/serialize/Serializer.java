package demo.serialize;


import demo.serialize.impl.JSONSerializer;

/**
 * TODO
 *
 * @author lsk
 * @interface_name Serializer
 * @date 2019/2/12
 */
public interface Serializer {
    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);

    Serializer DEFAULT = new JSONSerializer();
}
