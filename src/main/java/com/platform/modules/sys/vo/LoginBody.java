package com.platform.modules.sys.vo;

import com.platform.common.utils.validation.ValidateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = ValidateGroup.LOGIN.class)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {ValidateGroup.LOGIN.class, ValidateGroup.PASSWORD.class})
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空", groups = ValidateGroup.PASSWORD.class)
    private String newPassword;

}
