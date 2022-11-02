package com.platform.common.config;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import com.platform.common.constant.Constants;
import com.platform.common.utils.redis.RedisUtils;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码配置类
 */
@Component
@Slf4j
public class CaptchaConfig {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成验证码
     *
     * @throws IOException
     */
    public Dict easyCaptcha() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 获取运算的结果
        String code;
        try {
            code = new Double(Double.parseDouble(captcha.text())).intValue() + "";
        } catch (Exception e) {
            code = captcha.text();
        }
        // 唯一标识
        String uuid = IdUtil.simpleUUID();
        String key = Constants.CAPTCHA_KEY + uuid;
        redisUtils.set(key, code, Constants.CAPTCHA_TIMEOUT, TimeUnit.MINUTES);
        return Dict.create()
                .set("uuid", uuid)
                .set("img", captcha.toBase64());
    }

}
