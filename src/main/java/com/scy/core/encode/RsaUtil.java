package com.scy.core.encode;

import com.scy.core.format.MessageUtil;
import com.scy.core.model.RsaKeyBO;
import lombok.extern.slf4j.Slf4j;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RsaUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/3.
 */
@Slf4j
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

    public static String getSign(String data, String privateKeyBase64Str) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64Str));
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update(data.getBytes());

            byte[] signByte = signature.sign();
            return Base64Util.encodeBase64String(signByte);
        } catch (Exception e) {
            log.error(MessageUtil.format("getSign error", e, "data", data));
        }
        return null;
    }

    public static boolean checkSign(String data, String signBase64Str, String publicKeyBase64Str) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE);

            byte[] publicKeyByte = Base64Util.decodeBase64(publicKeyBase64Str);
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByte));

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(data.getBytes());

            return signature.verify(Base64Util.decodeBase64(signBase64Str));
        } catch (Exception e) {
            log.error(MessageUtil.format("checkSign error", e, "data", data, "signBase64Str", signBase64Str));
            return Boolean.FALSE;
        }
    }
}
