package com.akazam.json.utils;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jayway.jsonpath.Criteria.where;

/**
 * @ClassName JSONUtils
 * @Description 根据JSONPath，解析JSON数据
 * @Author Alex
 * @Date 2019/2/19 9:28
 * @Version 1.0
 **/
public class JSONUtils {
    private static final String FILTER_KEY_VAL = "json.path.key.val";
    private static final String FILTER_KEY_PREFIX = "json.path.filter.";

    public static List<Map<String, Object>> json2List(String jsonData, List<Map<String, String>> defVal) {
        List<Map<String, String>> keyVals = ConfigUtils.getListMapByKey(FILTER_KEY_VAL, defVal, ",", ":");
        List<Map<String, Object>> resultList = new ArrayList<>();
        ReadContext context = JsonPath.parse(jsonData);

        for (Map<String, String> map : keyVals) {
            for (String key : map.keySet()) {
                resultList.addAll(getResultList(context, key, map.get(key)));
            }
        }
        return resultList;
    }

    private static Filter getFilter(String key, String val) {
        return Filter.filter(where(key).is(val));
    }

    private static List<Map<String, Object>> getResultList(ReadContext context, String key, String val) {
        Filter mapFilter = getFilter(key, val);
        return context.read(ConfigUtils.getStringByKey(FILTER_KEY_PREFIX.concat(val), FILTER_KEY_PREFIX), mapFilter);
    }
}
