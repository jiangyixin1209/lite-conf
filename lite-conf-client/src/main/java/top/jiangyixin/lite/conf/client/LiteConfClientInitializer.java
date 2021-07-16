package top.jiangyixin.lite.conf.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 上午9:43
 */
@Slf4j
@Component
public class LiteConfigClientInitializer implements ApplicationContextAware, InitializingBean {
    
    @Override
    public void afterPropertiesSet() throws Exception {
    
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    
    }
}
