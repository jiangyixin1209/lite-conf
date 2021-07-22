package top.jiangyixin.lite.conf.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;
import top.jiangyixin.lite.conf.core.annotation.LiteConf;
import top.jiangyixin.lite.conf.client.util.ClassUtils;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 上午9:41
 */
public class SpringLiteConfigClient implements ApplicationContextAware, InitializingBean {
    
    public static void init(String basePackages) {
        LiteConfClientInitializer initializer = new LiteConfClientInitializer();
        Map<String, Object> beanMap = new HashMap<>();
        try{
            if (!StringUtils.hasText(basePackages)) {
                throw new RuntimeException("扫描包路径不能为空");
            }
            String[] packages = basePackages.split(",");
            for (String pkg : packages) {
                Set<Class<?>> classes = ClassUtils.scanClassFromPackagePath(pkg);
                for (Class<?> cls : classes) {
                    if (cls.isAnnotationPresent(LiteConf.class)) {
                        beanMap.put(cls.getName(), cls.newInstance());
                    }
                }
            }
            initializer.confBeanCheck(beanMap);
        } catch (Exception e) {
            throw new RuntimeException("Lite Conf 初始化失败", e);
        }
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    
    }
}
