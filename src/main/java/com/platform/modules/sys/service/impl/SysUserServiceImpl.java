package com.platform.modules.sys.service.impl;

import com.platform.modules.sys.enums.SysUserTypeEnum;
import com.platform.common.exception.BaseException;
import com.platform.common.shiro.utils.Md5Utils;
import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.domain.SysRole;
import com.platform.modules.sys.domain.SysUser;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.sys.service.SysRoleService;
import com.platform.modules.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户 业务层处理
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Resource
    private SysUserDao userDao;

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysConfigService configService;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(userDao);
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> queryList(SysUser user) {
        user.setUserType(SysUserTypeEnum.USER);
        return userDao.queryList(user);
    }

    @Override
    public List<SysUser> queryListMore(SysUser user) {
        // 搜索用户
        List<SysUser> users = queryList(user);
        if (CollectionUtils.isEmpty(users)) {
            return users;
        }
        for (SysUser sysUser : users) {
            SysRole sysRole = roleService.getById(sysUser.getRoleId());
            if (sysRole != null) {
                sysUser.setRoleName(sysRole.getRoleName());
            }
        }
        return users;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser queryByUserName(String userName) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        return queryOne(sysUser);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer addUser(SysUser user) {
        uniqueUserName(user);
        // 盐
        String salt = Md5Utils.salt();
        user.setSalt(salt);
        user.setPassword(Md5Utils.credentials(user.getPassword(), salt));
        // 新增用户信息
        int rows = 0;
        try {
            rows = add(user);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            checkException(e);
        }
        return rows;
    }

    private void checkException(org.springframework.dao.DuplicateKeyException e) {
        if (e.getMessage().contains("user_name")) {
            throw new BaseException("用户账号已存在");
        }
        throw new BaseException("不能重复提交，请重试");
    }

    /**
     * 查询唯一
     */
    private void uniqueUserName(SysUser sysUser) {
        SysUser query = new SysUser();
        query.setUserName(sysUser.getUserName());
        SysUser user = queryOne(query);
        boolean result = user == null
                || !(sysUser.getUserId() == null)
                || user.getUserId().equals(sysUser.getUserId());
        if (!result) {
            throw new BaseException("用户账号已存在");
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateUser(SysUser user) {
        user.setUserName(null);
        return updateById(user);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Integer changeStatus(SysUser user) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(user.getUserId());
        sysUser.setStatus(user.getStatus());
        return updateById(sysUser);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Integer updateProfile(SysUser user) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(user.getUserId());
        sysUser.setNickName(user.getNickName());
        sysUser.setPhone(user.getPhone());
        sysUser.setEmail(user.getEmail());
        sysUser.setSex(user.getSex());
        return updateById(sysUser);
    }

    @Override
    public void updatePwd(Long userId, String password) {
        String salt = Md5Utils.salt();
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setSalt(salt);
        user.setPassword(Md5Utils.credentials(password, salt));
        updateById(user);
    }

    /**
     * 删除用户信息
     *
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteUser(Long userId) {
        return this.deleteById(userId);
    }

    @Override
    public Integer updateAvatar(Long userId, String path) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setAvatar(path);
        return updateById(user);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport) {
        if (CollectionUtils.isEmpty(userList) || userList.size() == 0) {
            throw new BaseException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.quertConfigByKey("sys_user_initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = queryByUserName(user.getUserName());
                if (u == null) {
                    String salt = Md5Utils.salt();
                    user.setPassword(Md5Utils.credentials(Md5Utils.md5(password), salt));
                    user.setSalt(salt);
                    this.add(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUserId(u.getUserId());
                    this.updateById(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BaseException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
