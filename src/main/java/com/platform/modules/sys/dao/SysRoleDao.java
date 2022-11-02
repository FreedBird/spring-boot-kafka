package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.domain.SysRole;

import java.util.List;

/**
 * 角色表 数据层
 */
public interface SysRoleDao extends BaseMapper<SysRole> {
    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> queryList(SysRole role);

}
