package com.platform.modules.sys.service;

import com.platform.common.web.service.BaseService;
import com.platform.modules.sys.domain.SysRole;

/**
 * 角色业务层
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    Integer addRole(SysRole role);

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    Integer updateRole(SysRole role);

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    Integer changeStatus(SysRole role);

    /**
     * 批量删除角色信息
     *
     * @param roleId 需要删除的角色ID
     * @return 结果
     */
    Integer deleteRole(Long roleId);
}
