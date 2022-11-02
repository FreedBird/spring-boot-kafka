package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.domain.SysMenu;

import java.util.List;

/**
 * 菜单表 数据层
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {
    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenu> queryList(SysMenu menu);

    /**
     * 查询所有权限
     *
     * @return 权限列表
     */
    List<String> queryAllPerms();

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<String> queryPermsByRoleId(Long roleId);

    /**
     * 根据用户角色查询系统菜单列表
     *
     * @param roleId 用户角色
     * @return 菜单列表
     */
    List<SysMenu> queryListByRoleId(Long roleId);

    /**
     * 查询菜单
     *
     * @return 菜单列表
     */
    List<SysMenu> queryAllMenuTree();

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 用户角色
     * @return 菜单列表
     */
    List<SysMenu> queryMenuTreeByRoleId(Long roleId);

}
