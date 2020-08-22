package com.scy.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * UrlBO
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/22.
 */
@Getter
@Setter
@ToString
public class UrlBO {

    private String protocol;

    private String host;

    private Integer port;

    private String file;

    private String query;

    private String authority;

    private String path;
}
