package top.jiangyixin.lite.conf.client.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 上午10:00
 */
@Slf4j
public class PropertiesUtils {
    public static final String PROPERTY_NAME = "/lite-conf.properties";
    
    private PropertiesUtils() {}
    private static Properties properties;
    
    static {
        properties = new Properties();
        try(InputStream inputStream = PropertiesUtils.class.getResourceAsStream(PROPERTY_NAME)){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bufferedReader);
        } catch (Exception e) {
            log.error("加载{}文件出错", PROPERTY_NAME, e);
            throw new RuntimeException("加载lite-conf.properties文件出错", e);
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
}
