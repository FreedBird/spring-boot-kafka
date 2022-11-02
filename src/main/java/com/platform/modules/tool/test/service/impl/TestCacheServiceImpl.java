package com.platform.modules.tool.test.service.impl;

import cn.hutool.core.lang.Dict;
import com.platform.modules.tool.test.service.TestCacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "test:abc")
public class TestCacheServiceImpl implements TestCacheService {

    @Override
    @Cacheable(key = "#key")
    public Dict test1(Long key) {
        Dict dict = Dict.create()
                .set("time", System.currentTimeMillis())
                .set("key", key);
        return dict;
    }

    @Override
    @CacheEvict(key = "#key")
    public void test2(Long key) {

    }

}
