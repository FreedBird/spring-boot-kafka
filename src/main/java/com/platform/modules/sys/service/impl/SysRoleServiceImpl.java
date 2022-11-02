package com.platform.modules.sys.service.impl;

import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.common.exception.BaseException;
import com.platform.modules.sys.dao.SysRoleDao;
import com.platform.modules.sys.dao.SysRoleMenuDao;
import com.platform.modules.sys.domain.SysRole;
import com.platform.modules.sys.domain.SysRoleMenu;
import com.platform.modules.sys.domain.SysUser;
import com.platform.modules.sys.service.SysRoleService;
import com.platform.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色 业务层处理
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Resource
    private SysRoleDao roleDao;

    @Resource
    private SysRoleMenuDao roleMenuDao;

    @Resource
    private SysUserService sysUserService;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(roleDao);
    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> queryList(SysRole role) {
        return roleDao.queryList(role);
    }

    /**
     * 校验名称是否唯一
     *
     * @return 结果
     */
    private void uniqueName(SysRole sysRole) {
        SysRole query = new SysRole();
        query.setRoleName(sysRole.getRoleName());
        SysRole role = queryOne(query);
        boolean result = role == null
                || !(sysRole.getRoleId() == null)
                || role.getRoleId().equals(sysRole.getRoleId());
        if (!result) {
            throw new BaseException("角色名称已存在");
        }
    }

    /**
     * 校验标识是否唯一
     *
     * @return 结果
     */
    private void uniqueKey(SysRole sysRole) {
        SysRole query = new SysRole();
        query.setRoleKey(sysRole.getRoleKey());
        SysRole role = queryOne(query);
        boolean result = role == null
                || !(sysRole.getRoleId() == null)
                || role.getRoleId().equals(sysRole.getRoleId());
        if (!result) {
            throw new BaseException("权限字符已存在");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer addRole(SysRole role) {
        uniqueName(role);
        uniqueKey(role);
        // 新增角色信息
        Integer result = 0;
        try {
            result = this.add(role);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            checkException(e);
        }
        // 新增角色菜单关联信息
        this.saveRoleMenu(role);
        return result;
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateRole(SysRole role) {
        uniqueName(role);
        uniqueKey(role);
        // 修改角色信息
        Integer result = 0;
        try {
            result = this.updateById(role);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            checkException(e);
        }
        // 删除角色与菜单关联
        roleMenuDao.deleteByRoleId(role.getRoleId());
        // 新增角色与菜单关联
        this.saveRoleMenu(role);
        return result;
    }

    private void checkException(org.springframework.dao.DuplicateKeyException e) {
        if (e.getMessage().contains("role_name")) {
            throw new BaseException("角色名称已存在");
        }
        if (e.getMessage().contains("role_key")) {
            throw new BaseException("权限字符已存在");
        }
        throw new BaseException("不能重复提交，请重试");
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Integer changeStatus(SysRole role) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(role.getRoleId());
        sysRole.setStatus(role.getStatus());
        return updateById(sysRole);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteRole(Long roleId) {
        // 校验
        SysUser sysUser = new SysUser();
        sysUser.setRoleId(roleId);
        sysUserService.queryCount(sysUser);
        if (sysUserService.queryCount(sysUser) > 0) {
            throw new BaseException("角色已分配,不能删除");
        }
        // 删除关联
        roleMenuDao.deleteByRoleId(roleId);
        // 删除角色
        return this.deleteById(roleId);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    private void saveRoleMenu(SysRole role) {
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            roleMenuDao.batchRoleMenu(list);
        }
    }

}
