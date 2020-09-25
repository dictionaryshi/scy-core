package com.scy.core.encode;

import com.scy.core.StringUtil;
import com.scy.core.format.MessageUtil;
import com.scy.core.model.RsaKeyBO;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
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

    public static final String PROVIDER = "SunRsaSign";

    public static final int DEFAULT_LENGTH = 1024;

    public static RsaKeyBO createKey(int length) {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(SIGN_TYPE, PROVIDER);
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
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE, PROVIDER);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64Str));
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS, PROVIDER);
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
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE, PROVIDER);

            byte[] publicKeyByte = Base64Util.decodeBase64(publicKeyBase64Str);
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByte));

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS, PROVIDER);
            signature.initVerify(publicKey);
            signature.update(data.getBytes());

            return signature.verify(Base64Util.decodeBase64(signBase64Str));
        } catch (Exception e) {
            log.error(MessageUtil.format("checkSign error", e, "data", data, "signBase64Str", signBase64Str));
            return Boolean.FALSE;
        }
    }

    public static String rsaEncrypt(String privateKeyBase64Str, String data) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE, PROVIDER);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64Str));
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            Key key = KeyFactory.getInstance(SIGN_TYPE, PROVIDER).generatePublic(rsaPublicKeySpec);

            Cipher cipher = Cipher.getInstance(SIGN_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptDataBytes = cipher.doFinal(data.getBytes());
            return Base64Util.encodeBase64String(encryptDataBytes);
        } catch (Exception e) {
            log.error(MessageUtil.format("rsaEncrypt error", e, "data", data));
            return StringUtil.EMPTY;
        }
    }

    public static String rsaDecrypt(String publicKeyBase64Str, String encryptData) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE, PROVIDER);
            byte[] publicKeyByte = Base64Util.decodeBase64(publicKeyBase64Str);
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByte));

            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            Key key = KeyFactory.getInstance(SIGN_TYPE, PROVIDER).generatePrivate(rsaPrivateKeySpec);

            Cipher cipher = Cipher.getInstance(SIGN_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] encryptDataBytes = Base64Util.decodeBase64(encryptData);
            byte[] dataBytes = cipher.doFinal(encryptDataBytes);
            return new String(dataBytes);
        } catch (Exception e) {
            log.error(MessageUtil.format("rsaDecrypt error", e, "encryptData", encryptData));
            return StringUtil.EMPTY;
        }
    }
}
