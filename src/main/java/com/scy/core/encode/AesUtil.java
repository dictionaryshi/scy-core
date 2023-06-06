package com.scy.core.encode;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author : shichunyang
 * Date    : 2023/6/6
 * Time    : 3:49 下午
 * ---------------------------------------
 * Desc    : AesUtil
 */
public class AesUtil {

    private static final String AES = "AES";

    private static final int DEFAULT_LENGTH = 128;

    public static final String SHA1_PRNG = "SHA1PRNG";

    public static SecretKeySpec generateKey(String key, int length) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);

            SecureRandom secureRandom = SecureRandom.getInstance(SHA1_PRNG);
            secureRandom.setSeed(key.getBytes());

            keyGenerator.init(length, secureRandom);
            return new SecretKeySpec(keyGenerator.generateKey().getEncoded(), AES);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String data, SecretKeySpec secretKeySpec) {
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptData, SecretKeySpec secretKeySpec) {
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptData));
            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
