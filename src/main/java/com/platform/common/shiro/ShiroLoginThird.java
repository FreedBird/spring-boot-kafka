package com.platform.common.shiro;

import com.platform.common.shiro.enums.ShiroUserTypeEnum;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 */
@Data
public class ShiroLoginThird implements AuthenticationToken {

    private String unionId;
    private ShiroUserTypeEnum typeEnum;

    public ShiroLoginThird(String unionId, ShiroUserTypeEnum typeEnum) {
        this.unionId = unionId;
        this.typeEnum = typeEnum;
    }

    @Override
    public Object getPrincipal() {
        return unionId;
    }

    @Override
    public Object getCredentials() {
        return unionId;
    }

    public ShiroUserTypeEnum getTypeEnum() {
        return typeEnum;
    }
}
