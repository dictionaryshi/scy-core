package com.scy.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * RsaKeyBO
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/3.
 */
@Getter
@Setter
@ToString
public class RsaKeyBO {

    private int length;

    private String publicKey;

    private String privateKey;
}
