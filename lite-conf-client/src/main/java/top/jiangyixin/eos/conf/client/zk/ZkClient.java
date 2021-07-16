package top.jiangyixin.eos.conf.client.zk;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import top.jiangyixin.eos.conf.client.common.EosConstant;
import top.jiangyixin.eos.conf.client.core.ConfigRefreshCallBack;

/**
 * zookeeper客户端
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/15 下午4:37
 */
@Slf4j
public class ZkClient {
    private static final AtomicBoolean isRegister = new AtomicBoolean(false);
    private static CuratorFramework client = null;
    
    private static class ZkClientHolder {
        private final static ZkClient INSTANCE = new ZkClient();
    }
    
    /**
     * 获取zk实例
     * @param url zk连接url
     * @return ZkClient
     */
    public static ZkClient getInstance(String url) {
        if (client == null) {
            synchronized (ZkClient.class) {
                if (client == null) {
                    // sessionTimeoutMs 解决程序退出后临时节点没删除的问题，尽快删除节点
                    client = CuratorFrameworkFactory.builder()
                                     .connectString(url)
                                     .sessionTimeoutMs(1000)
                                     .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                     .build();
                    client.start();
                }
            }
        }
        return ZkClientHolder.INSTANCE;
    }
    
    /**
     * 获取所有服务端地址
     *  服务端启动时会注册地址到zk中
     * @return 服务端地址列表 ["127.0.0.1:8080", "127.0.0.1:8081"]
     */
    public List<String> getAllServer() {
        try{
            return client.getChildren().forPath(EosConstant.ZK_SERVER_REGISTRY);
        } catch (Exception e) {
            log.error("获取服务端地址异常", e);
        }
        return null;
    }
    
    /**
     * 创建根目录
     */
    public void createRootPath() {
        try{
            Stat stat = client.checkExists().forPath(EosConstant.ZK_ROOT_PATH);
            if (stat == null) {
                client.create().withMode(CreateMode.PERSISTENT).forPath(EosConstant.ZK_ROOT_PATH);
            }
        } catch (Exception e) {
            log.error("创建根目录失败", e);
        }
    }
    
    /**
     * 注册服务
     * @param address 服务地址
     */
    public void registerServer(String address) {
        doRegisterServer(address);
        addRetryServerListener(address);
    }
    
    /**
     * zk注册服务地址
     * @param address 服务地址
     */
    public void doRegisterServer(String address) {
        try{
            Stat stat = client.checkExists().forPath(EosConstant.ZK_SERVER_REGISTRY);
            if (stat == null) {
                client.create().withMode(CreateMode.EPHEMERAL).forPath(EosConstant.ZK_SERVER_REGISTRY);
            }
            String serverAddressPath = EosConstant.ZK_SERVER_REGISTRY.concat("/").concat(address);
            // 两种情况会进行服务的注册
            // 1. 启动时注册服务
            // 2. 客户端断开，重新注册服务，即使客户端断开连接，临时节点也需要延迟N秒后才会消失，需要循环判断注册
            for (int i = 0; i <= Integer.getInteger(EosConstant.ZK_CHECK_TEMP_TIME, 30); i++) {
                if (client.checkExists().forPath(serverAddressPath) == null) {
                    client.create().withMode(CreateMode.EPHEMERAL).forPath(serverAddressPath);
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
                log.info("服务注册中。。。。");
            }
        } catch (Exception e) {
            log.error("服务注册失败", e);
        }
        
    }
    
    /**
     * 注册断开重连监听器
     * @param address 服务地址
     */
    public void addRetryServerListener(String address) {
        ZkServerConnectionStateListener stateListener = new ZkServerConnectionStateListener(
                address, this);
        client.getConnectionStateListenable().addListener(stateListener);
    }
    
    /**
     * 创建节点
     * @param path 节点路径
     * @param mode 节点模式
     */
    public void createNode(String path, CreateMode mode) {
        try{
            if (CreateMode.EPHEMERAL == mode) {
                for (int i = 1; i <= Integer.getInteger(EosConstant.ZK_CHECK_TEMP_TIME, 30); i++) {
                    Stat stat = client.checkExists().forPath(path);
                    if (stat == null) {
                        client.create().withMode(mode).forPath(path);
                        isRegister.compareAndSet(false, true);
                        break;
                    }
                    if (!isRegister.get()) {
                        TimeUnit.SECONDS.sleep(1);
                        log.info("注册节点中。。。。。");
                    }
                }
            } else {
                Stat stat = client.checkExists().forPath(path);
                if (stat == null) {
                    // 如果父节点不存在，则在创建节点的同时创建父节点
                    client.create().creatingParentsIfNeeded().withMode(mode).forPath(path);
                }
            }
        } catch (Exception e) {
            log.error("创建节点失败", e);
        }
    }
    
    /**
     * 监控节点是否被修改
     * @param path 节点路径
     * @param callBack 回调
     */
    public void monitor(String path, ConfigRefreshCallBack callBack) {
        try{
            NodeCache nodeCache = new NodeCache(client, path);
            nodeCache.getListenable().addListener(() -> {
                callBack.call(path);
            });
            nodeCache.start();
        } catch (Exception e) {
            log.error("监控节点{}异常", path);
        }
    }
}
