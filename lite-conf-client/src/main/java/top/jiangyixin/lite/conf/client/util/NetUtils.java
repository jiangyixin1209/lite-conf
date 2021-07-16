package top.jiangyixin.lite.conf.client.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author jiangyixin
 * @version 1.0
 * @date 2021/7/16 下午4:31
 */
@Slf4j
public class NetUtils {
    
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");
    public static final String ANY_HOST = "0.0.0.0";
    public static final String LOCAL_HOST = "127.0.0.1";
    private static volatile InetAddress LOCAL_ADDRESS = null;
    
    
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null && !ANY_HOST.equals(name) && !LOCAL_HOST.equals(name) && IP_PATTERN.matcher(name).matches());
    }
    
    public static boolean isLocalHost(String host) {
        return host != null && (LOCAL_IP_PATTERN.matcher(host).matches() || "localhost".equalsIgnoreCase(
                host));
    }
    
    public static boolean isAnyHost(String host) {
        return ANY_HOST.equals(host);
    }
    
    /**
     * 遍历本地网卡，返回第一个合理的IP。
     *
     * @return 本地网卡IP
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }
    
    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            log.warn("Failed to retrieving ip address, " + e.getMessage(), e);
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            try {
                                InetAddress address = addresses.nextElement();
                                if (isValidAddress(address)) {
                                    return address;
                                }
                            } catch (Throwable e) {
                                log.warn("Failed to retrieving ip address, " + e.getMessage(), e);
                            }
                        }
                    } catch (Throwable e) {
                        log.warn("Failed to retrieving ip address, " + e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            log.warn("Failed to retrieving ip address, " + e.getMessage(), e);
        }
        log.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }
}
