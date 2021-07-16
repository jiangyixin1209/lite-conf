package top.jiangyixin.lite.conf.client.core;

/**
 * 配置刷新回调接口
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/15 下午5:43
 */
public interface ConfigRefreshCallBack {
    
    /**
     * 刷新回调
     * @param path 节点
     */
    void call(String path);
}
