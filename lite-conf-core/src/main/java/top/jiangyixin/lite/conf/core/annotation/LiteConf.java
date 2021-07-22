package top.jiangyixin.lite.conf.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author jiangyixin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LiteConf {
    
    /** 配置名称，不填写默认为类名 **/
    String value() default "";
    
    /** 系统名称，标识在哪个系统使用此配置 **/
    String system() default "";
    
    /** key前缀 **/
    String prefix() default "";
    
    /** 系统所属环境，例如 test、prod **/
    String env() default "";
    
    /** 是否设置为环境变量 **/
    boolean isSetEnv() default true;
}
