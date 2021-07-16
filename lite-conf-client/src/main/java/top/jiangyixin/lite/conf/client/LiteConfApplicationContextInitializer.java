package top.jiangyixin.lite.conf.client;

import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 上午9:52
 */
public class LiteConfigApplicationContextInitializer implements ApplicationContextInitializer {
    private final static AtomicBoolean INIT = new AtomicBoolean(false);
    
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        if (INIT.compareAndSet(false, true)) {
            LiteConfigClient.init();
        }
    }
}
