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
package com.platform.common.web.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 基础service接口层基类
 */
public interface BaseService<T> {

    /**
     * 新增（并返回id）
     *
     * @param entity
     * @return
     */
    Integer add(T entity);

    /**
     * 根据 id 删除
     *
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     * 根据ids 批量删除
     *
     * @param ids
     * @return
     */
    Integer deleteByIds(Long[] ids);

    /**
     * 根据 id 修改
     *
     * @param entity
     * @return
     */
    Integer updateById(T entity);

    /**
     * 根据 id 查询
     *
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 根据 ids 查询
     *
     * @param idList
     * @return
     */
    List<T> getByIds(Collection<? extends Serializable> idList);

    /**
     * 查询总记录数
     *
     * @param t
     * @return
     */
    Long queryCount(T t);

    /**
     * 查询全部记录
     *
     * @param t
     * @return
     */
    List<T> queryList(T t);

    /**
     * 查询一个
     *
     * @param t
     * @return
     */
    T queryOne(T t);

}
