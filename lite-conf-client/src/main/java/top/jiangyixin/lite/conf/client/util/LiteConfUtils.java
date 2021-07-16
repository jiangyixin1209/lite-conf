package top.jiangyixin.lite.conf.client.util;

import org.springframework.util.StringUtils;
import top.jiangyixin.lite.conf.client.common.LiteConfConstant;
import top.jiangyixin.lite.conf.client.enumerate.DataLoadTypeEnum;
import top.jiangyixin.lite.conf.client.zk.ZkClient;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 上午9:55
 */
public class LiteConfUtils {
    
    /**
     * 获取扫描包路径
     * @return 包路径
     */
    public static String getConfigPackagePaths() {
        // 加载环境变量
        String paths = System.getProperty(LiteConfConstant.CONF_PACKAGES);
        if (StringUtils.hasText(paths)) {
            return paths;
        }
        // 加载配置文件
        paths = PropertiesUtils.getProperty(LiteConfConstant.CONF_PACKAGES);
        if (StringUtils.hasText(paths)) {
            return paths;
        }
        throw new RuntimeException("请配置 lite.conf.package");
    }
    
    /**
     * 获取本地配置数据加载方式
     *  local 只加载本地默认配置
     *  remote 加载配置中心配置
     * @return DataLoadTypeEnum
     */
    public static DataLoadTypeEnum getDataLoadType() {
        String status = System.getProperty(LiteConfConstant.DATA_LOAD_TYPE);
        if (StringUtils.hasText(status)) {
            return DataLoadTypeEnum.valueOf(status);
        }
        status = PropertiesUtils.getProperty(LiteConfConstant.DATA_LOAD_TYPE);
        if (StringUtils.hasText(status)) {
            return DataLoadTypeEnum.valueOf(status);
        }
        return DataLoadTypeEnum.REMOTE;
    }
    
    /**
     * 获取本机IP
     * @return 本机IP
     */
    public static String getLocalIp() {
        String ip = System.getProperty(LiteConfConstant.SERVER_IP);
        if (StringUtils.hasText(ip)) {
            return ip;
        }
        ip = PropertiesUtils.getProperty(LiteConfConstant.SERVER_IP);
        if (StringUtils.hasText(ip)) {
            return ip;
        }
        return NetUtils.getLocalAddress().getHostAddress();
    }
    
    /**
     * 获取本机服务端口
     * @return 本机服务端口
     */
    public static String getServerPort() {
        String port = System.getProperty(LiteConfConstant.SERVER_PORT);
        if (StringUtils.hasText(port)) {
            return port;
        }
        port = PropertiesUtils.getProperty(LiteConfConstant.SERVER_PORT);
        if (!StringUtils.hasText(port)) {
            throw new RuntimeException("请配置" + LiteConfConstant.SERVER_PORT);
        }
        return port;
    }
    
    /**
     * 获取Zookeeper Client
     * @return Zookeeper Client
     */
    public static ZkClient getZkClient() {
        String zkUrl = System.getProperty(LiteConfConstant.ZK_URL);
        if (StringUtils.hasText(zkUrl)) {
            return ZkClient.getInstance(zkUrl);
        }
        zkUrl = PropertiesUtils.getProperty(LiteConfConstant.ZK_URL);
        if (zkUrl == null) {
            throw new RuntimeException("请配置" + LiteConfConstant.ZK_URL);
        }
        return ZkClient.getInstance(zkUrl);
    }
}
