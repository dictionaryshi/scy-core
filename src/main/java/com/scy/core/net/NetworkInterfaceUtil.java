package com.scy.core.net;

import com.scy.core.CollectionUtil;
import com.scy.core.StringUtil;
import com.scy.core.format.MessageUtil;
import com.scy.core.model.UrlBO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * NetworkInterfaceUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/1.
 */
@Slf4j
public class NetworkInterfaceUtil {

    private static String localIp;

    public static List<String> getIps() {
        List<String> ips = CollectionUtil.newArrayList();

        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (Throwable e) {
            log.error(MessageUtil.format("getIps error", e));
            return CollectionUtil.emptyList();
        }

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress.isLoopbackAddress()) {
                    continue;
                }
                if (inetAddress instanceof Inet6Address) {
                    continue;
                }

                try {
                    if (inetAddress.isReachable(100)) {
                        ips.add(inetAddress.getHostAddress());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ips;
    }

    public static String getIp() {
        if (!StringUtil.isEmpty(localIp)) {
            return localIp;
        }

        List<String> ips = getIps();
        if (CollectionUtil.isEmpty(ips)) {
            return StringUtil.EMPTY;
        }

        localIp = ips.get(0);

        return localIp;
    }

    public static String getIp(InetSocketAddress inetSocketAddress) {
        if (Objects.isNull(inetSocketAddress)) {
            return StringUtil.EMPTY;
        }

        InetAddress address = inetSocketAddress.getAddress();
        return address.getHostAddress();
    }

    public static String getIpPort(int port) {
        String ip = getIp();
        return getIpPort(ip, port);
    }

    public static String getIpPort(String ip, int port) {
        if (Objects.isNull(ip)) {
            return null;
        }

        return ip.concat(StringUtil.COLON).concat(String.valueOf(port));
    }

    public static UrlBO parseIpPort(String address) {
        if (StringUtil.isEmpty(address)) {
            return null;
        }

        String[] splitArr = address.split(StringUtil.COLON);

        String host = splitArr[0];

        int port = Integer.parseInt(splitArr[1]);

        UrlBO urlBO = new UrlBO();
        urlBO.setHost(host);
        urlBO.setPort(port);
        return urlBO;
    }
}
