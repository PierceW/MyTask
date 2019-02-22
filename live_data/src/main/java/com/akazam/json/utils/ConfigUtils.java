package com.akazam.json.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @ClassName ConfigUtils
 * @Description TODO
 * @Author Alex
 * @Date 2019/2/19 9:36
 * @Version 1.0
 **/
public class ConfigUtils {

    private static Properties props = new Properties();

    static {
        try {
            InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream("json-parse.properties");
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConfigUtils() {}

    public static List<Map<String, String>> getListMapByKey(String key, List<Map<String, String>> defaultVal
            , String delimiter1, String delimiter2) {
        String vals = getStringByKey(key, "");
        List<Map<String, String>> listMapData = new ArrayList<>();
        if (StringUtils.isNotEmpty(vals)) {
            for (String val : vals.split(delimiter1)) {
                if (StringUtils.isNoneEmpty(val) && val.contains(delimiter2)) {
                    String[] keyVal = val.split(delimiter2);
                    if (keyVal.length == 2) {
                        Map<String, String> map = new HashMap<>();
                        map.put(keyVal[0], keyVal[1]);
                        listMapData.add(map);
                    }
                }
            }
        }
        return listMapData.isEmpty() ? defaultVal : listMapData;
    }

    public static String[] getStringArrayByKey(String key, String[] defaultVal, String delimiter) {
        String vals = getStringByKey(key, "");
        return StringUtils.isEmpty(vals) ? defaultVal : vals.split(delimiter);
    }

    public static String getStringByKey(String key, String defaultVal) {
        return props.getProperty(key, defaultVal);
    }

}
