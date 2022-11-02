package com.platform.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.platform.common.constant.Constants;
import com.platform.common.shiro.vo.LoginUser;
import com.platform.common.utils.redis.RedisUtils;
import com.platform.common.web.page.PageDomain;
import com.platform.common.web.page.TableDataInfo;
import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.monitor.domain.SysUserOnline;
import com.platform.modules.sys.service.SysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 在线用户 服务层处理
 */
@Service
public class SysUserOnlineServiceImpl extends BaseServiceImpl<String> implements SysUserOnlineService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public TableDataInfo querySysUser(String userName, PageDomain pageDomain) {
        Collection<String> keys = redisUtils.keys(Constants.TOKEN_SYS + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<>();
        // 制作分页
        keys = CollUtil.sub(keys, pageDomain.getPageStart(), pageDomain.getPageEnd());
        if (!CollectionUtils.isEmpty(keys)) {
            for (String key : keys) {
                if (!redisUtils.hasKey(key)) {
                    continue;
                }
                LoginUser loginUser = JSONUtil.toBean(redisUtils.get(key), LoginUser.class);
                userOnlineList.add(formatUser(loginUser));
            }
        }
        return getDataTable(userOnlineList, keys.size());
    }

    /**
     * 格式化用户信息
     *
     * @param loginUser
     * @return
     */
    private SysUserOnline formatUser(LoginUser loginUser) {
        if (loginUser == null) {
            return null;
        }
        SysUserOnline online = new SysUserOnline();
        online.setTokenId(loginUser.getTokenId());
        online.setUserName(loginUser.getSysUser().getUserName());
        online.setIpAddr(loginUser.getIpAddr());
        online.setLoginLocation(loginUser.getLoginLocation());
        online.setBrowser(loginUser.getBrowser());
        online.setOs(loginUser.getOs());
        online.setLoginTime(loginUser.getLoginTime());
        return online;
    }

    @Override
    public void logout(String tokenId) {
        redisUtils.delete(Constants.TOKEN_SYS + tokenId);
    }
}
