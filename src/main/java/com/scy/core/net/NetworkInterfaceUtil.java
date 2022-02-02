package com.scy.core.net;

import com.scy.core.CollectionUtil;
import com.scy.core.StringUtil;
import com.scy.core.format.MessageUtil;
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
}
