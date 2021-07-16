package top.jiangyixin.lite.conf.client;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import top.jiangyixin.lite.conf.client.annotation.LiteConf;
import top.jiangyixin.lite.conf.client.annotation.LiteConfField;
import top.jiangyixin.lite.conf.client.common.LiteConfConstant;
import top.jiangyixin.lite.conf.client.enumerate.DataLoadTypeEnum;
import top.jiangyixin.lite.conf.client.util.LiteConfUtils;
import top.jiangyixin.lite.conf.client.util.ReflectUtils;
import top.jiangyixin.lite.conf.client.zk.ZkClient;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 上午9:43
 */
@Slf4j
@Component
public class LiteConfClientInitializer implements ApplicationContextAware, InitializingBean {
    
    /**
     * 存储所有配置信息
     * key: 类的全名 org.conf.demo.conf.Biz
     * value: 类的实例
     */
    private final static Map<String, Object> LOCAL_CONF_DATA_MAP = new ConcurrentHashMap<>();
    
    /**
     * 存储配置信息的key对应的bean
     * key: /lite_conf/env/system/confFileName
     * value: org.conf.demo.conf.Biz
     */
    private final static Map<String, String> CONF_NAME_LOCAL_CONF_DATA_MAP = new ConcurrentHashMap<>();
    
    public String getConfClassName(String key) {
        if (key == null) {
            return null;
        }
        return CONF_NAME_LOCAL_CONF_DATA_MAP.get(key);
    }
    
    public Object getConf(String className) {
        if (className == null) {
            return null;
        }
        return LOCAL_CONF_DATA_MAP.get(className);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    
    }
    
    public void confBeanCheck(Map<String, Object> beanMap) {
        if (beanMap == null || beanMap.isEmpty()) {
            return;
        }
        for (Object bean : beanMap.values()) {
            String className = bean.getClass().getName();
            LiteConf liteConf = bean.getClass().getAnnotation(LiteConf.class);
            if (liteConf == null) {
                throw new RuntimeException("此Bean["+className+"]未有LiteConf注解");
            }
            String system = liteConf.system();
            if (!StringUtils.hasText(system)) {
                throw new RuntimeException("["+className+"]中的LiteConf注解必须配置system");
            }
            String confName = liteConf.value();
            if (StringUtils.hasText(confName)) {
                if (confName.contains(":") || confName.contains("&") || confName.contains("：")) {
                    throw new RuntimeException("["+className+"]中的配置类配置名称不能包含:和&字符");
                }
            }
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                // 检查 LiteConfField
                if (!field.isAnnotationPresent(LiteConfField.class)) {
                    throw new RuntimeException("["+className+"]中的配置类中所有字段必须加上LiteConfField注解");
                }
                LiteConfField liteConfField = field.getAnnotation(LiteConfField.class);
                String desc = liteConfField.value();
                if (!StringUtils.hasText(desc)) {
                    throw new RuntimeException("["+className+"]中的配置类中所有字段的LiteConfField必须有注释描述");
                }
                // 检查字段类型
                if (!LiteConfConstant.SUPPORT_VALUE_TYPE.contains(field.getType().getName())) {
                    throw new RuntimeException("["+className+"]中的配置类字段只支持以下类型"
                                                       + LiteConfConstant.SUPPORT_VALUE_TYPE.toString());
                }
                // 检查默认值
                Object value = ReflectUtils.callGetMethod(field.getName(), bean);
                if (StringUtils.isEmpty(value)) {
                    throw new RuntimeException("["+className+"]中的配置类中所有字段必须有默认值");
                }
                
            }
        }
    }
    
    public void init(Map<String, Object> beanMap, boolean isWatchNode, boolean isOverwrite) {
        if (beanMap == null || beanMap.isEmpty()) {
            return;
        }
        for (Object bean : beanMap.values()) {
            String className = bean.getClass().getName();
            LiteConf liteConf = bean.getClass().getAnnotation(LiteConf.class);
            String configFileName = getConfigFileName(bean, liteConf);
            String system = liteConf.system();
            String prefix = liteConf.prefix();
            String env = liteConf.env();
            boolean isSetEnv = liteConf.isSetEnv();
            
            if (isWatchNode && LiteConfUtils.getDataLoadType() == DataLoadTypeEnum.REMOTE) {
                String localIp = LiteConfUtils.getLocalIp().concat(":").concat(LiteConfUtils.getServerPort());
                ZkClient zkClient = LiteConfUtils.getZkClient();
//                String configFileNode = ZkClient.buildPath()
//                zkClient.createNode();
            }
            
        }
    }
    
    private String getConfigFileName(Object bean, LiteConf liteConf) {
        String fileName = liteConf.value();
        if (!StringUtils.hasText(fileName)) {
            fileName = bean.getClass().getSimpleName();
        }
        return fileName;
    }
}
