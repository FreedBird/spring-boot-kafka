package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.exception.BaseException;
import com.platform.common.web.service.impl.BaseServiceImpl;
import com.platform.modules.sys.dao.SysConfigDao;
import com.platform.modules.sys.domain.SysConfig;
import com.platform.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数配置 服务层实现
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig> implements SysConfigService {

    @Resource
    private SysConfigDao configDao;

    @Autowired
    public void setBaseDao() {
        super.setBaseDao(configDao);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String quertConfigByKey(String configKey) {
        SysConfig sysConfig = configDao.selectOne(new QueryWrapper<>(new SysConfig(configKey)));
        return sysConfig == null ? "" : sysConfig.getConfigValue();
    }

    @Override
    public Integer addConfig(SysConfig config) {
        uniqueKey(config);
        Integer result = 0;
        try {
            result = this.add(config);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            checkException(e);
        }
        return result;
    }

    @Override
    public Integer updConfig(SysConfig config) {
        uniqueKey(config);
        Integer result = 0;
        try {
            result = this.updateById(config);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            checkException(e);
        }
        return result;
    }

    /**
     * 校验名称是否唯一
     *
     * @return 结果
     */
    private void uniqueKey(SysConfig sysConfig) {
        SysConfig query = new SysConfig();
        query.setConfigKey(sysConfig.getConfigKey());
        SysConfig config = queryOne(query);
        boolean result = config == null
                || !(sysConfig.getConfigId() == null)
                || config.getConfigId().equals(sysConfig.getConfigId());
        if (!result) {
            throw new BaseException("参数键名已存在");
        }
    }

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfig> queryList(SysConfig config) {
        return configDao.queryList(config);
    }

    private void checkException(org.springframework.dao.DuplicateKeyException e) {
        if (e.getMessage().contains("config_key")) {
            throw new BaseException("参数键名已存在");
        }
        throw new BaseException("不能重复提交，请重试");
    }
}
