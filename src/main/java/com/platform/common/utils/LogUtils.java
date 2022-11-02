package com.platform.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.platform.common.enums.YesOrNoEnum;
import com.platform.common.utils.ip.AddressUtils;
import com.platform.common.utils.ip.IpUtils;
import com.platform.common.utils.timer.TimerUtils;
import com.platform.modules.monitor.domain.SysLoginLog;
import com.platform.modules.monitor.domain.SysOperLog;
import com.platform.modules.monitor.service.SysLoginLogService;
import com.platform.modules.monitor.service.SysOperLogService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志工具类
 */
@Slf4j
public class LogUtils {

    /**
     * 记录登陆信息
     */
    public static void recordLogin(final String username, final YesOrNoEnum status, final String message,
                                   final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ipAddr = IpUtils.getIpAddr(ServletUtils.getRequest());
        String ipLocation = AddressUtils.getRealAddressByIP(ipAddr);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ipAddr));
        s.append(ipLocation);
        s.append(getBlock(username));
        s.append(status.getInfo());
        s.append(getBlock(message));
        // 打印信息到日志
        log.info(s.toString(), args);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setUserName(username);
        loginLog.setIpAddr(ipAddr);
        loginLog.setIpLocation(ipLocation);
        loginLog.setBrowser(browser);
        loginLog.setOs(os);
        loginLog.setMsg(message);
        // 日志状态
        loginLog.setStatus(status);
        // 时间
        loginLog.setCreateTime(DateUtil.date());
        TimerUtils.instance().addTask((timeout) -> {
            // 插入数据
            SpringUtil.getBean(SysLoginLogService.class).add(loginLog);
        });
    }

    /**
     * 操作日志记录
     */
    public static void recordOper(final SysOperLog operLog) {
        TimerUtils.instance().addTask((timeout) -> {
            SpringUtil.getBean(SysOperLogService.class).add(operLog);
        });
    }

    private static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
