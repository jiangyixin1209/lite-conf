package top.jiangyixin.lite.conf.client.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * ZK断连监听器
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/15 下午4:52
 */
public class ZkServerConnectionStateListener implements ConnectionStateListener {
    
    private final String address;
    private final ZkClient client;
    
    public ZkServerConnectionStateListener(String address, ZkClient client) {
        this.address = address;
        this.client = client;
    }
    
    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        client.doRegisterServer(address);
    }
}
