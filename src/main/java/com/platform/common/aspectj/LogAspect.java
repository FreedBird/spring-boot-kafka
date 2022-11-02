package com.platform.common.aspectj;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.platform.common.aspectj.lang.annotation.Log;
import com.platform.common.enums.YesOrNoEnum;
import com.platform.common.shiro.ShiroUtils;
import com.platform.common.shiro.vo.LoginUser;
import com.platform.common.utils.LogUtils;
import com.platform.common.utils.ServletUtils;
import com.platform.common.utils.ip.AddressUtils;
import com.platform.common.utils.ip.IpUtils;
import com.platform.modules.monitor.domain.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.platform.common.aspectj.lang.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // 获取当前的用户
            LoginUser loginUser = ShiroUtils.getLoginUser();
            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(YesOrNoEnum.YES);
            operLog.setErrorMsg("-");
            // 请求的地址
            String operIp = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(operIp);
            // 返回参数
            operLog.setJsonResult(JSONUtil.toJsonStr(jsonResult));
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null) {
                operLog.setOperName(loginUser.getSysUser().getUserName());
            }
            if (e != null) {
                operLog.setStatus(YesOrNoEnum.NO);
                operLog.setErrorMsg(StrUtil.sub(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 设置操作时间
            operLog.setOperTime(DateUtil.date());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            // 远程查询操作地点
            String address = AddressUtils.getRealAddressByIP(operLog.getOperIp());
            operLog.setOperLocation(address);
            // 保存数据库
            LogUtils.recordOper(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage(), e);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog) {
        // 设置action动作
        operLog.setBusinessType(log.businessType());
        // 设置标题
        operLog.setTitle(log.title());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) {
        String requestMethod = operLog.getRequestMethod();
        if ("POST".equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StrUtil.sub(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StrUtil.sub(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    params += JSONUtil.toJsonStr(paramsArray[i]) + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

}
