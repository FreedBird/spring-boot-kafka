package com.platform.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.enums.YesOrNoEnum;
import com.platform.common.exception.BaseException;
import com.platform.common.shiro.ShiroUtils;
import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.sys.dao.SysMenuDao;
import com.platform.modules.sys.dao.SysRoleMenuDao;
import com.platform.modules.sys.domain.SysMenu;
import com.platform.modules.sys.domain.vo.MetaVo;
import com.platform.modules.sys.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单 业务层处理
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {

    @Resource
    private SysMenuDao menuDao;

    @Resource
    private SysRoleMenuDao roleMenuDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(menuDao);
    }

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> queryList(SysMenu menu) {
        List<SysMenu> menuList;
        // 管理员显示所有菜单信息
        if (ShiroUtils.isAdmin()) {
            menuList = menuDao.queryList(menu);
        } else {
            menuList = menuDao.queryListByRoleId(menu.getRoleId());
        }
        return menuList;
    }

    @Override
    public Set<String> queryPerms(Long roleId) {
        List<String> perms;
        if (0L == roleId) {
            perms = menuDao.queryAllPerms();
        } else {
            perms = menuDao.queryPermsByRoleId(roleId);
        }
        Set<String> permsSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(perms)) {
            for (String perm : perms) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Tree<String>> getRouters() {
        List<SysMenu> menus;
        if (ShiroUtils.isAdmin()) {
            menus = menuDao.queryAllMenuTree();
        } else {
            menus = menuDao.queryMenuTreeByRoleId(ShiroUtils.getSysRole().getRoleId());
        }
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<>();
        }
        menus = menus.stream().filter(menu -> "M".equals(menu.getMenuType()) || "C".equals(menu.getMenuType())).collect(Collectors.toList());
        return buildRouterTree(menus);
    }

    private List<Tree<String>> buildRouterTree(List<SysMenu> menus) {
        // 顺序排序
        Collections.sort(menus, Comparator.comparing(SysMenu::getOrderNum));
        // 定义返回值
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("pid");
        treeNodeConfig.setNameKey("title");
        List<Tree<String>> treeList = TreeUtil.build(menus, "0", treeNodeConfig, (sysMenu, tree) -> {
            tree.setId(String.valueOf(sysMenu.getMenuId()));
            tree.setParentId(String.valueOf(sysMenu.getParentId()));
            tree.setName(sysMenu.getMenuName());
            tree.putExtra("hidden", !YesOrNoEnum.YES.equals(sysMenu.getVisible()));
            tree.putExtra("name", StringUtils.capitalize(sysMenu.getPath()));
            tree.putExtra("path", getRouterPath(sysMenu));
            tree.putExtra("component", StringUtils.isEmpty(sysMenu.getComponent()) ? "Layout" : sysMenu.getComponent());
            tree.putExtra("meta", new MetaVo(sysMenu.getMenuName(), sysMenu.getIcon()));
            if ("M".equals(sysMenu.getMenuType())) {
                tree.putExtra("redirect", "noRedirect");
                tree.putExtra("alwaysShow", true);
            }
        });
        return treeList;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    private String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录
        if (0L == menu.getParentId() && YesOrNoEnum.NO.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }

    @Override
    public List<Tree<String>> getMenuTree(SysMenu menu) {
        List<SysMenu> menus;
        if (ShiroUtils.isAdmin()) {
            menus = menuDao.queryAllMenuTree();
        } else {
            menus = menuDao.queryMenuTreeByRoleId(menu.getRoleId());
        }
        return buildMenuTree(menus);
    }

    private List<Tree<String>> buildMenuTree(List<SysMenu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return null;
        }
        // 顺序排序
        Collections.sort(menus, Comparator.comparing(SysMenu::getOrderNum));
        // 定义返回值
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("pid");
        treeNodeConfig.setNameKey("label");
        List<Tree<String>> treeList = TreeUtil.build(menus, "0", treeNodeConfig, (sysMenu, tree) -> {
            tree.setId(String.valueOf(sysMenu.getMenuId()));
            tree.setParentId(String.valueOf(sysMenu.getParentId()));
            tree.setName(sysMenu.getMenuName());
        });
        return treeList;
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Long> queryListByRoleId(Long roleId) {
        List<SysMenu> menus = menuDao.queryListByRoleId(roleId);
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList();
        }
        return menus.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    private boolean hasChildByMenuId(Long menuId) {
        SysMenu menu = new SysMenu();
        menu.setParentId(menuId);
        return menuDao.selectOne(new QueryWrapper<>(menu)) != null;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public Integer addMenu(SysMenu menu) {
        menu.setMenuId(DateUtil.currentSeconds());
        return add(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        return updateById(menu);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenu(Long menuId) {
        if (hasChildByMenuId(menuId)) {
            throw new BaseException("存在子菜单,不允许删除");
        }
        if (roleMenuDao.countByMenuId(menuId) > 0) {
            throw new BaseException("菜单已分配,不允许删除");
        }
        return deleteById(menuId);
    }

}
