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
package com.platform.common.utils.validation;

/**
 * 校验入参字段默认分组
 */
public interface ValidateGroup {

    /**
     * 新增
     */
    interface ADD {
    }

    /*
     * 修改
     */
    interface EDIT {
    }

    /**
     * 删除
     */
    interface DELETE {
    }

    /*
     * 登陆
     */
    interface LOGIN {
    }

    /*
     * 修改密码
     */
    interface PASSWORD {
    }

}
