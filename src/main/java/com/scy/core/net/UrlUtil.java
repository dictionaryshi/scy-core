package com.scy.core.net;

import com.scy.core.format.MessageUtil;
import com.scy.core.model.UrlBO;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * UrlUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/22.
 */
@Slf4j
public class UrlUtil {

    private UrlUtil() {
    }

    public static UrlBO parseUrl(String url) {
        URL netUrl;
        try {
            netUrl = new URL(url);
        } catch (Throwable e) {
            log.error(MessageUtil.format("parseUrl error", e, "url", url));
            return null;
        }

        UrlBO urlBO = new UrlBO();
        urlBO.setProtocol(netUrl.getProtocol());
        urlBO.setHost(netUrl.getHost());
        urlBO.setPort(netUrl.getPort());
        urlBO.setFile(netUrl.getFile());
        urlBO.setQuery(netUrl.getQuery());
        urlBO.setAuthority(netUrl.getAuthority());
        urlBO.setPath(netUrl.getPath());
        return urlBO;
    }
}
