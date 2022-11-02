package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.domain.SysUser;

import java.util.List;

/**
 * 用户表 数据层
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> queryList(SysUser sysUser);

}
