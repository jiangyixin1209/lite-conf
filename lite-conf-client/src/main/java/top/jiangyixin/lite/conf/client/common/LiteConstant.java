package top.jiangyixin.lite.conf.client.common;

import java.util.Arrays;
import java.util.List;

/**
 * Eos配置常量类
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/15 下午4:32
 */
public final class LiteConstant {
    
    /** 根目录 **/
    public static final String ZK_ROOT_PATH = "/eos_conf";
    
    /** 服务端注册目录 **/
    public static final String ZK_SERVER_REGISTRY = ZK_ROOT_PATH.concat("/server_registry");
    
    /** 配置支持的数据类型 **/
    public static List<String> SUPPORT_VALUE_TYPE = Arrays.asList(
            "java.lang.String",
            "java.lang.Long",
            "java.lang.Double",
            "java.lang.Integer",
            "java.util.Map",
            "map",
            "int",
            "long",
            "double"
    );
    
    /**
     * zk创建临时节点时检查的时间,单位秒
     *
     * 由于即使客户端断开连接，临时节点也需要延迟N秒后才会消失
     * 所以要循环检查是否存在，如果超过检测时间则认为这个节点是存在的临时节点
     */
    public static final String ZK_CHECK_TEMP_TIME = "zookeeper.check.temp.time";
    
}
