package com.scy.core.net;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author : shichunyang
 * Date    : 2022/1/30
 * Time    : 6:20 下午
 * ---------------------------------------
 * Desc    : SocketUtil
 */
@Slf4j
public class SocketUtil {

    private SocketUtil() {
    }

    public static boolean checkAddress(String address, int port, int timeout) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(address, port), timeout);
            return socket.isConnected();
        } catch (Exception e) {
            log.info(MessageUtil.format("checkAddress fail", e, "address", address, "port", port));
            return Boolean.FALSE;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
