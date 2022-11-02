package com.platform.modules.sys.service;

import com.platform.common.web.service.BaseService;
import com.platform.modules.sys.domain.SysUser;

import java.util.List;

/**
 * 用户 业务层
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 查询全部记录
     *
     * @param user
     * @return
     */
    List<SysUser> queryListMore(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser queryByUserName(String userName);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    Integer addUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    Integer updateUser(SysUser user);

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    Integer changeStatus(SysUser user);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    Integer updateProfile(SysUser user);

    /**
     * 修改用户密码
     *
     * @return 结果
     */
    void updatePwd(Long userId, String password);

    /**
     * 通过用户ID删除用户
     *
     * @return 结果
     */
    Integer deleteUser(Long userId);

    /**
     * 修改头像
     *
     * @return 结果
     */
    Integer updateAvatar(Long userId, String path);

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    String importUser(List<SysUser> userList, Boolean isUpdateSupport);
}
