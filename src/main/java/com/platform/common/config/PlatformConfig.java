package com.platform.common.config;

import com.platform.common.core.EnumUtils;
import com.platform.common.enums.YesOrNoEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 */
@Component
public class PlatformConfig {

    /**
     * 上传路径
     */
    public static String profile;

    /**
     * 验证码开关
     */
    public static YesOrNoEnum verifyCode;

    /**
     * 获取地址开关
     */
    public static YesOrNoEnum ipAddress;

    /**
     * token超时时间（分钟）
     */
    public static Integer tokenTimeout;

    /**
     * 定时任务状态
     */
    public static boolean jobStatus;

    @Value("${platform.verifyCode}")
    public void setVerifyCode(String verifyCode) {
        PlatformConfig.verifyCode = EnumUtils.toEnum(YesOrNoEnum.class, verifyCode, YesOrNoEnum.YES);
    }

    @Value("${platform.profile}")
    public void setProfile(String profile) {
        PlatformConfig.profile = profile;
    }

    @Value("${platform.ipAddress}")
    public void setIpAddress(String ipAddress) {
        PlatformConfig.ipAddress = EnumUtils.toEnum(YesOrNoEnum.class, ipAddress, YesOrNoEnum.YES);
    }

    @Value("${platform.tokenTimeout}")
    public void setTokenTimeout(Integer tokenTimeout) {
        PlatformConfig.tokenTimeout = tokenTimeout;
    }

    @Value("${platform.jobStatus}")
    public void setJobStatus(String jobStatus) {
        PlatformConfig.jobStatus = YesOrNoEnum.YES.equals(EnumUtils.toEnum(YesOrNoEnum.class, jobStatus));
    }

}