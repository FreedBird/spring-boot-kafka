package com.platform.modules.sys.service;

import com.platform.common.web.page.PageDomain;
import com.platform.common.web.page.TableDataInfo;

/**
 * 在线用户 服务层
 */
public interface SysUserOnlineService {

    /**
     * 查询在线用户
     *
     * @return
     */
    TableDataInfo querySysUser(String userName, PageDomain pageDomain);

    /**
     * 强退
     *
     * @param tokenId
     */
    void logout(String tokenId);

}
