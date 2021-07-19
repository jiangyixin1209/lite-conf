package top.jiangyixin.lite.conf.client.util;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/19 下午3:08
 */
public class BeanUtils {
    
    public static final String MAP = "java.util.Map";
    
    public static void setValue(Field field, Object bean, Object value)
            throws IllegalAccessException {
        if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
            field.set(bean, Integer.parseInt(value.toString()));
        } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
            field.set(bean, Long.parseLong(value.toString()));
        } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
            field.set(bean, Double.parseDouble(value.toString()));
        } else if (MAP.equals(field.getType().getName())) {
            field.set(bean, JsonUtils.toBean(Map.class, value.toString().replaceAll("'", "\"")));
        } else {
            field.set(bean, value);
        }
    }
}
