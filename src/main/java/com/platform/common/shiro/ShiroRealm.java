package com.platform.common.shiro;

import cn.hutool.core.date.DateUtil;
import com.platform.common.constant.Constants;
import com.platform.common.enums.ResultCodeEnum;
import com.platform.common.enums.YesOrNoEnum;
import com.platform.common.exception.LoginException;
import com.platform.common.shiro.enums.ShiroUserTypeEnum;
import com.platform.common.shiro.utils.Md5Utils;
import com.platform.common.shiro.vo.LoginUser;
import com.platform.common.utils.ServletUtils;
import com.platform.common.utils.ip.AddressUtils;
import com.platform.common.utils.ip.IpUtils;
import com.platform.modules.sys.domain.SysRole;
import com.platform.modules.sys.domain.SysUser;
import com.platform.modules.sys.service.SysMenuService;
import com.platform.modules.sys.service.SysRoleService;
import com.platform.modules.sys.service.SysTokenService;
import com.platform.modules.sys.service.SysUserService;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Set;

/**
 * ShiroRealm
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysTokenService sysTokenService;

    /**
     * 提供用户信息，返回权限信息
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object object = ShiroUtils.getSubject().getPrincipal();
        if (object == null) {
            return null;
        }
        // 后台管理
        if (object instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) object;
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            if (ShiroUserTypeEnum.SYS_USER.equals(loginUser.getUserType())) {
                // 添加角色
                info.addRole(loginUser.getSysRole().getRoleKey());
                // 添加权限
                info.addStringPermissions(loginUser.getPermissions());
            }
            return info;
        }
        return null;
    }

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof ShiroLoginToken
                || authenticationToken instanceof ShiroLoginAuth
                || authenticationToken instanceof ShiroLoginThird;
    }

    /**
     * 身份认证/登录，验证用户是不是拥有相应的身份； 用于登陆认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // token
        if (authenticationToken instanceof ShiroLoginToken) {
            String token = (String) authenticationToken.getPrincipal();
            ShiroUserTypeEnum userType = ((ShiroLoginToken) authenticationToken).getUserType();
            LoginUser loginUser = sysTokenService.queryByToken(token, userType);
            if (loginUser == null) {
                throw new LoginException(ResultCodeEnum.UNAUTHORIZED);
            }
            // 加密盐
            String salt = Md5Utils.salt();
            // 对token加密
            String credentials = Md5Utils.credentials(token, salt);
            return new SimpleAuthenticationInfo(loginUser, credentials, ByteSource.Util.bytes(salt), getName());
        }
        // 账号密码登录
        if (authenticationToken instanceof ShiroLoginAuth) {
            ShiroLoginAuth token = (ShiroLoginAuth) authenticationToken;
            String username = token.getUsername();
            ShiroUserTypeEnum typeEnum = token.getTypeEnum();
            // 后台用户
            if (ShiroUserTypeEnum.SYS_USER.equals(typeEnum)) {
                return makeSysLoginUser(username);
            }
        }
        return null;
    }

    /**
     * 组装登录对象
     *
     * @return
     */
    private SimpleAuthenticationInfo makeSysLoginUser(String username) {
        // 后台用户
        SysUser sysUser = sysUserService.queryByUserName(username);
        // 处理用户
        if (sysUser == null) {
            throw new AuthenticationException("用户不存在"); // 账号不存在
        }
        if (!YesOrNoEnum.YES.equals(sysUser.getStatus())) {
            throw new LoginException("账号已停用"); // 账号禁用
        }
        // 查询角色
        SysRole role = initRole(sysUser);
        // 查询权限
        Set<String> permissions = sysMenuService.queryPerms(role.getRoleId());
        permissions.add(Constants.PERM_SYS);
        LoginUser loginUser = new LoginUser(sysUser, role, permissions);
        // 设置代理信息
        makeUserAgent(loginUser);
        // 登录
        return new SimpleAuthenticationInfo(loginUser, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()), getName());
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    private void makeUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        // 登录ip
        loginUser.setIpAddr(ip);
        // 登录地点
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        // 浏览器类型
        loginUser.setBrowser(userAgent.getBrowser().getName());
        // 操作系统
        loginUser.setOs(userAgent.getOperatingSystem().getName());
        // 登录时间
        loginUser.setLoginTime(DateUtil.currentSeconds());
    }

    private SysRole initRole(SysUser sysUser) {
        if (ShiroUtils.isAdmin(sysUser)) {
            return new SysRole(0L);
        }
        // 查询角色
        SysRole role = sysRoleService.getById(sysUser.getRoleId());
        if (role == null) {
            throw new LoginException("角色不存在");
        }
        if (!YesOrNoEnum.YES.equals(role.getStatus())) {
            throw new LoginException("角色被禁用");
        }
        return role;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        return ShiroUtils.isAdmin() || super.isPermitted(principals, permission);
    }

    public static void main(String[] args) {
        String password = Md5Utils.md5("1");
        String credentialsSalt = "salt";
        System.out.println(Md5Utils.credentials(password, credentialsSalt));
    }
}
