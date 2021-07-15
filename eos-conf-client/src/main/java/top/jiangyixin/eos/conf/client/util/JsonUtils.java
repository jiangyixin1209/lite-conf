package top.jiangyixin.eos.conf.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * Json工具类
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/15 下午4:20
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public static ObjectMapper getMapper() { return MAPPER; }
    
    public static String toString(Object object) { return toJson(object); }
    
    public static String toJson(Object object) {
        try{
            StringWriter writer = new StringWriter();
            MAPPER.writeValue(writer, object);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("序列化对象【"+object+"】时出错", e);
        }
    }
    public static <T> T toBean(Class<T> cls, String json) {
        try{
            return MAPPER.readValue(json, cls);
        } catch (Exception e) {
            throw new RuntimeException("JSON【"+json+"】转对象时出错", e);
        }
    }
}
