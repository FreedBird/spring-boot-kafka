package com.platform.modules.sys.dao;

import com.platform.modules.sys.domain.SysRoleMenu;

import java.util.List;

/**
 * 角色与菜单关联表 数据层
 */
public interface SysRoleMenuDao {
    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    Integer countByMenuId(Long menuId);

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    Integer deleteByRoleId(Long roleId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    Integer batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
