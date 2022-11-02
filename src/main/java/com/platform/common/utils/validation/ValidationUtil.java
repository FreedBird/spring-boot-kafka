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

import com.platform.common.exception.BaseException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 字段入參校驗
 */
public class ValidationUtil {

    /**
     * 使用hibernate的注解来进行验证
     */
    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
            .buildValidatorFactory().getValidator();

    /**
     * 功能描述: <br>
     * 〈注解验证参数〉
     *
     * @param obj
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <T> void validate(T obj, Class<?>... groups) {
        if (obj == null) {
            throw new BaseException("对象不能为空");
        }
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, groups);
        // 抛出检验异常
        if (constraintViolations.size() > 0) {
            throw new BaseException(constraintViolations.iterator().next().getMessage());
        }
    }

}
