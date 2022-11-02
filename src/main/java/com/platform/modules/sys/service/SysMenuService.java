package com.platform.modules.sys.service;

import cn.hutool.core.lang.tree.Tree;
import com.platform.common.web.service.BaseService;
import com.platform.modules.sys.domain.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * 菜单 业务层
 */
public interface SysMenuService extends BaseService<SysMenu> {

    /**
     * 查询权限
     *
     * @return 权限列表
     */
    Set<String> queryPerms(Long roleId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Long> queryListByRoleId(Long roleId);

    /**
     * 查询菜单树信息
     *
     * @return 菜单列表
     */
    List<Tree<String>> getRouters();

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menu
     * @return
     */
    List<Tree<String>> getMenuTree(SysMenu menu);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    Integer addMenu(SysMenu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int updateMenu(SysMenu menu);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int deleteMenu(Long menuId);

}
