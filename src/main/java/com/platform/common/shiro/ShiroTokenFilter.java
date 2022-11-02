package com.platform.common.shiro;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.platform.common.constant.Constants;
import com.platform.common.enums.ResultCodeEnum;
import com.platform.common.exception.LoginException;
import com.platform.common.shiro.enums.ShiroUserTypeEnum;
import com.platform.common.utils.ServletUtils;
import com.platform.common.web.domain.AjaxResult;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * auth2过滤器
 */
public class ShiroTokenFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) {
        //获取请求token
        ShiroLoginToken token = getToken(request);
        if (token == null) {
            return null;
        }
        return token;
    }

    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        ShiroLoginToken token = getToken(request);
        if (token == null) {
            return error(response, ResultCodeEnum.FAIL, "token失效，请重新登录");
        }
        try {
            getSubject(request, response).login(token);
            return true;
        } catch (LoginException e) {
            return error(response, ResultCodeEnum.UNAUTHORIZED, e.getMessage());
        } catch (AuthenticationException e) {
            return error(response, ResultCodeEnum.FAIL, "token失效，请重新登录");
        }
    }

    private boolean error(ServletResponse response, ResultCodeEnum resultCode, String msg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.getWriter().print(JSONUtil.toJsonStr(AjaxResult.result(resultCode, msg)));
        return false;
    }

    /**
     * 获取请求的token
     */
    private ShiroLoginToken getToken(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = ServletUtils.getRequest().getServletPath();
        if (path.startsWith(Constants.PREFIX_APP)) {
            String token = httpRequest.getHeader(Constants.TOKEN_APP_KEY);
            if (StringUtils.isEmpty(token)) {
                token = queryToken(httpRequest);
            }
            if (!StringUtils.isEmpty(token)) {
                return new ShiroLoginToken(token, ShiroUserTypeEnum.APP_USER);
            }
        } else {
            String token = httpRequest.getHeader(Constants.TOKEN_SYS_KEY);
            if (StringUtils.isEmpty(token)) {
                token = queryToken(httpRequest);
            }
            if (!StringUtils.isEmpty(token)) {
                return new ShiroLoginToken(token, ShiroUserTypeEnum.SYS_USER);
            }
        }
        return null;
    }

    /**
     * 查询token
     *
     * @param httpRequest
     * @return
     */
    private String queryToken(HttpServletRequest httpRequest) {
        Cookie cookie = ServletUtil.getCookie(httpRequest, Constants.COOKIE_NAME);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

}
