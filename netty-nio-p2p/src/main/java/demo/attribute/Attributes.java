package demo.attribute;

import demo.session.Session;
import io.netty.util.AttributeKey;

/**
 * TODO
 *
 * @author lsk
 * @class_name Attributes
 * @date 2019/2/12
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
