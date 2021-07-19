package top.jiangyixin.lite.conf.client.core;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/15 下午5:46
 */
@Slf4j
public class ConfRefreshCallBackImpl implements ConfRefreshCallBack {
    
    @Override
    public void call(String path) {
    
    }
    
    private void executeCustomCallBack(Object bean, Conf config) {
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        if (interfaces.length > 0) {
            for (Class<?> cls : interfaces) {
                if (ConfUpdateCustomCallBack.class.getSimpleName().equals(cls.getSimpleName())) {
                    try{
                        Method method = bean.getClass().getDeclaredMethod("reload", Conf.class);
                        method.invoke(bean, config);
                    } catch (Exception e) {
                        log.error("执行用户自定义回调方法异常", e);
                    }
                    break;
                }
            }
        }
    }
}
