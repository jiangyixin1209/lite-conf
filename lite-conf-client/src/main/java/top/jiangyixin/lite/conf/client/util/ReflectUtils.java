package top.jiangyixin.lite.conf.client.util;

import java.lang.reflect.Method;
import org.springframework.util.StringUtils;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 上午11:48
 */
public class ReflectUtils {
    
    public static Object callGetMethod(String field, Object instance) {
        Object result = null;
        try{
            String fieldGetMethodName = "get".concat(StringUtils.capitalize(field));
            Method method;
            try{
                method = getMethod(instance.getClass(), fieldGetMethodName);
            } catch (NoSuchMethodException e) {
                fieldGetMethodName = "is".concat(StringUtils.capitalize(field));
                method = getMethod(instance.getClass(), fieldGetMethodName);
            }
            result = method.invoke(instance);
        } catch (Exception e) {
            throw new RuntimeException("callGetMethod异常:" + e.getMessage());
        }
        return result;
    }
    
    public static Method getMethod(Class<?> cls, String methodName, Class<?>... type)
            throws NoSuchMethodException {
        try{
            return cls.getDeclaredMethod(methodName, type);
        } catch (NoSuchMethodException e) {
            Class<?> superCls = cls.getSuperclass();
            if (superCls != null && superCls != Object.class) {
                return getMethod(superCls, methodName, type);
            }
            throw e;
        }
    }
    
}
