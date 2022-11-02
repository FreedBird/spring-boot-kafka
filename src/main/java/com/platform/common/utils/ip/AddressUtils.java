package com.platform.common.utils.ip;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.common.config.PlatformConfig;
import com.platform.common.core.CharsetKit;
import com.platform.common.enums.YesOrNoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 获取地址类
 */
@Slf4j
public class AddressUtils {

    // IP地址查询
    private static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    private static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (YesOrNoEnum.YES.equals(PlatformConfig.ipAddress)) {
            try {
                String rspStr = HttpUtil.get(IP_URL + "?ip=" + ip + "&json=true", CharsetKit.CHARSET_GBK);
                if (StringUtils.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                JSONObject object = JSONUtil.parseObj(rspStr);
                String region = object.getStr("pro");
                String city = object.getStr("city");
                return StrUtil.format("{} {}", region, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return address;
    }
}
