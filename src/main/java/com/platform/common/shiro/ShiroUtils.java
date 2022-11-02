
package com.platform.common.shiro;

import com.platform.common.exception.BaseException;
import com.platform.common.shiro.vo.LoginUser;
import com.platform.modules.sys.domain.SysRole;
import com.platform.modules.sys.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static LoginUser getLoginUser() {
        return (LoginUser) getSubject().getPrincipal();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static SysUser getSysUser() {
        return getLoginUser().getSysUser();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getUsername() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            throw new BaseException("用户未登录，获取失败");
        }
        return loginUser.getSysUser().getUserName();
    }

    /**
     * 获取角色信息
     *
     * @return
     */
    public static SysRole getSysRole() {
        return getLoginUser().getSysRole();
    }

    /**
     * 管理员
     *
     * @return
     */
    public static boolean isAdmin() {
        return isAdmin(getLoginUser().getSysUser());
    }

    /**
     * 管理员
     *
     * @return
     */
    public static boolean isAdmin(SysUser sysUser) {
        if (sysUser == null) {
            return false;
        }
        return sysUser.isAdmin();
    }

}
