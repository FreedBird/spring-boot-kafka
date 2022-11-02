package com.platform.common.utils.bean;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanMapUtils {

    /**
     * 转换数据库和Java映射
     *
     * @param list
     * @return
     */
    public static Map<String, Map<String, Object>> converMap(List<Map<String, Object>> list) {
        Map<String, Map<String, Object>> mapMap = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            return mapMap;
        }
        for (Map<String, Object> mmm : list) {
            mapMap.put(String.valueOf(mmm.get("parentId")), mmm);
        }
        return mapMap;
    }

    public static Long toLong(Object s) {
        if (s == null) {
            return null;
        }
        if (s instanceof Long) {
            return (Long) s;
        }
        if (s instanceof String) {
            if (StringUtils.isEmpty((String) s)) {
                return null;
            }
            return Long.valueOf((String) s);
        }
        if (s instanceof Integer) {
            return Long.valueOf("" + s);
        }
        return null;
    }

    public static String toString(Object s) {
        if (s == null) {
            return "";
        }
        return s.toString();
    }

    public static Integer toInteger(Object s) {
        if (s == null) {
            return 0;
        }
        if (s instanceof Integer) {
            return (Integer) s;
        }
        if (s instanceof Long) {
            return ((Long) s).intValue();
        }
        if (s instanceof String) {
            if (StringUtils.isEmpty((String) s)) {
                return null;
            }
            return Integer.valueOf((String) s);
        }
        if (s instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) s;
            return bd.intValue();
        }
        if (s instanceof Double) {
            BigDecimal bd = new BigDecimal((Double) s);
            return bd.intValue();
        }
        return null;
    }

}
