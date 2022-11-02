package com.platform.common.shiro;

import com.platform.common.shiro.enums.ShiroUserTypeEnum;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 */
@Data
public class ShiroLoginAuth implements AuthenticationToken {

    private String username;
    private char[] password;
    private ShiroUserTypeEnum typeEnum;

    public ShiroLoginAuth(String username, String password, ShiroUserTypeEnum typeEnum) {
        this.username = username;
        this.password = password.toCharArray();
        this.typeEnum = typeEnum;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    public ShiroUserTypeEnum getTypeEnum() {
        return typeEnum;
    }
}
