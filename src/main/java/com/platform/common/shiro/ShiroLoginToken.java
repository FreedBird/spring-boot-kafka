package com.platform.common.shiro;

import com.platform.common.shiro.enums.ShiroUserTypeEnum;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 */
public class ShiroLoginToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;

    private String token;

    private ShiroUserTypeEnum userType;

    public ShiroLoginToken(String token, ShiroUserTypeEnum userType) {
        this.token = token;
        this.userType = userType;
    }

    public ShiroUserTypeEnum getUserType() {
        return userType;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
