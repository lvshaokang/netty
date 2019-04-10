package demo.utils;


import demo.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * TODO
 *
 * @author lsk
 * @class_name LoginUtil
 * @date 2019/2/12
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
