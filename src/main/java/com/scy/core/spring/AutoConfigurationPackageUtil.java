package com.scy.core.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;

import java.util.List;

/**
 * AutoConfigurationPackageUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/28.
 */
public class AutoConfigurationPackageUtil {

    private AutoConfigurationPackageUtil() {
    }

    /**
     * 获取自动配置包名
     */
    public static List<String> getAutoConfigurationPackage(BeanFactory beanFactory) {
        return AutoConfigurationPackages.get(beanFactory);
    }
}
