package demo.utils;

import java.util.UUID;

/**
 * TODO
 *
 * @author lsk
 * @class_name IDUtil
 * @date 2019-04-11
 */
public class IDUtil {
    public static String randomId(){
        return UUID.randomUUID().toString().split("-")[0];
    }
}
