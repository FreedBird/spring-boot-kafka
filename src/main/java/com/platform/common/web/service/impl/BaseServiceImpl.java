/**
 * Licensed to the Apache Software Foundation （ASF） under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * （the "License"）； you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * https://www.q3z3.com
 * QQ : 939313737
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.platform.common.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.platform.common.enums.ResultCodeEnum;
import com.platform.common.web.page.TableDataInfo;
import com.platform.common.web.service.BaseService;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 基础service实现层基类
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseMapper<T> baseDao;

    public void setBaseDao(BaseMapper<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public Integer add(T entity) {
        return baseDao.insert(entity);
    }

    @Override
    public Integer deleteById(Long id) {
        return baseDao.deleteById(id);
    }

    @Override
    public Integer deleteByIds(Long[] ids) {
        return baseDao.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public Integer updateById(T entity) {
        return baseDao.updateById(entity);
    }

    @Override
    public T getById(Long id) {
        return baseDao.selectById(id);
    }

    @Override
    public List<T> getByIds(Collection<? extends Serializable> idList) {
        return baseDao.selectBatchIds(idList);
    }

    @Override
    public Long queryCount(T t) {
        Wrapper<T> wrapper = new QueryWrapper<>(t);
        return baseDao.selectCount(wrapper).longValue();
    }

    @Override
    public List<T> queryList(T t) {
        Wrapper<T> wrapper = new QueryWrapper<>(t);
        return baseDao.selectList(wrapper);
    }

    @Override
    public T queryOne(T t) {
        Wrapper<T> wrapper = new QueryWrapper<>(t);
        return baseDao.selectOne(wrapper);
    }

    /**
     * 响应请求分页数据
     */
    protected TableDataInfo getDataTable(List<?> list, List<?> oldList) {
        Long total = new PageInfo(oldList).getTotal();
        return getDataTable(list, total.intValue());
    }

    /**
     * 格式化分页
     */
    public TableDataInfo getDataTable(List<?> list, Integer total) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(ResultCodeEnum.SUCCESS.getCode());
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }

}
