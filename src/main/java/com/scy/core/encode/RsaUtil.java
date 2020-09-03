package com.scy.core.encode;

import com.scy.core.model.RsaKeyBO;

import java.security.*;

/**
 * RsaUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/3.
 */
public class RsaUtil {

    private RsaUtil() {
    }

    /**
     * 签名类型
     */
    public static final String SIGN_TYPE = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1withRSA";

    public static final int DEFAULT_LENGTH = 1024;

    public static RsaKeyBO createKey(int length) {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(SIGN_TYPE);
        } catch (Throwable e) {
            return null;
        }

        keyPairGenerator.initialize(length);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RsaKeyBO rsaKeyBO = new RsaKeyBO();
        rsaKeyBO.setLength(length);

        PublicKey publicKey = keyPair.getPublic();
        rsaKeyBO.setPublicKey(Base64Util.encodeBase64String(publicKey.getEncoded()));

        PrivateKey privateKey = keyPair.getPrivate();
        rsaKeyBO.setPrivateKey(Base64Util.encodeBase64String(privateKey.getEncoded()));
        return rsaKeyBO;
    }
}
