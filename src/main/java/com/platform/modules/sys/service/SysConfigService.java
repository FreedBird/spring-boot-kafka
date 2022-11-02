package com.platform.modules.sys.service;

import com.platform.common.web.service.BaseService;
import com.platform.modules.sys.domain.SysConfig;

/**
 * 参数配置 服务层
 */
public interface SysConfigService extends BaseService<SysConfig> {

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String quertConfigByKey(String configKey);

    /**
     * 新增参数
     *
     * @param config
     * @return
     */
    Integer addConfig(SysConfig config);

    /**
     * 修改参数
     *
     * @param config
     * @return
     */
    Integer updConfig(SysConfig config);
}
