package com.platform.common.utils.gen.config;

import com.platform.common.core.EnumUtils;
import com.platform.common.enums.YesOrNoEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取代码生成相关配置
 */
@Component
public class GenConfig {
    /**
     * 作者
     */
    public static String author;

    /**
     * 生成包路径
     */
    public static String packageName;

    /**
     * 自动去除表前缀，默认是true
     */
    public static YesOrNoEnum autoRemovePre;

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String tablePrefix;

    @Value("${gen.author}")
    public void setAuthor(String author) {
        GenConfig.author = author;
    }

    @Value("${gen.packageName}")
    public void setPackageName(String packageName) {
        GenConfig.packageName = packageName;
    }

    @Value("${gen.autoRemovePre}")
    public void setAutoRemovePre(String autoRemovePre) {
        GenConfig.autoRemovePre = EnumUtils.toEnum(YesOrNoEnum.class, autoRemovePre, YesOrNoEnum.NO);
    }

    @Value("${gen.tablePrefix}")
    public void setTablePrefix(String tablePrefix) {
        GenConfig.tablePrefix = tablePrefix;
    }

}
