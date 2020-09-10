package com.scy.core.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisGeneratorRun
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/10.
 */
public class MyBatisGeneratorRun {

    public static void main(String[] args) throws Throwable {
        List<String> warnings = new ArrayList<>();
        File configFile = new File(MyBatisGeneratorRun.class.getResource("/generatorConfig.xml").toURI());
        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        Configuration configuration = configurationParser.parseConfiguration(configFile);
        DefaultShellCallback defaultShellCallback = new DefaultShellCallback(Boolean.TRUE);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, defaultShellCallback, warnings);
        myBatisGenerator.generate(null);
    }
}
