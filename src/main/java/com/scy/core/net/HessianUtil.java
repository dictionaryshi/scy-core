package com.scy.core.net;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.scy.core.format.MessageUtil;
import com.scy.core.reflect.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author : shichunyang
 * Date    : 2022/2/3
 * Time    : 10:33 下午
 * ---------------------------------------
 * Desc    : HessianUtil
 */
@Slf4j
public class HessianUtil {

    private HessianUtil() {
    }

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(byteArrayOutputStream);
        try {
            hessian2Output.writeObject(obj);
            hessian2Output.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error(MessageUtil.format("serialize error", e, "obj", obj));
            return null;
        } finally {
            try {
                hessian2Output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings(ClassUtil.UNCHECKED)
    public static <T> T deserialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Hessian2Input hessian2Input = new Hessian2Input(byteArrayInputStream);
        try {
            return (T) hessian2Input.readObject();
        } catch (IOException e) {
            log.error(MessageUtil.format("deserialize error", e));
            return null;
        } finally {
            try {
                hessian2Input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T clone2Obj(T object) {
        byte[] serialize = HessianUtil.serialize(object);
        return HessianUtil.deserialize(serialize);
    }
}
