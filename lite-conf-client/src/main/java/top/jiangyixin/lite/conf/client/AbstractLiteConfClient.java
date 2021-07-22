package top.jiangyixin.lite.conf.client;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.StringUtils;
import top.jiangyixin.lite.conf.core.annotation.LiteConf;
import top.jiangyixin.lite.conf.core.annotation.LiteConfField;
import top.jiangyixin.lite.conf.client.common.LiteConfConstant;
import top.jiangyixin.lite.conf.client.util.ReflectUtils;

/**
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 下午3:37
 */
public abstract class AbstractLiteConfClient {
    /**
     * 存储所有配置信息
     * key: 类的全名
     * value: 类的实例
     */
    private final static Map<String, Object> LOCAL_CONF_DATA_MAP = new ConcurrentHashMap<>();
    
    /**
     * 存储配置信息的key对应的bean
     * key:/lite_conf/{env}/{system}/{confFileName}
     * value:top.jiangyixin.conf.demo.conf.Biz
     */
    private final static Map<String, String> CONF_NAME_LOCAL_CONF_DATA_MAP = new ConcurrentHashMap<>();
    
    /**
     * 检查Bean是否合法
     * @param beanMap LiteConf实例集合
     */
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
}
