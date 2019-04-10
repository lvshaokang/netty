package demo.attribute;

import io.netty.util.AttributeKey;

/**
 * TODO
 *
 * @author lsk
 * @class_name Attributes
 * @date 2019/2/12
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
