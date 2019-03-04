package com.alex.test.akazam;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @ClassName PraseJsonEntity
 * @Description TODO
 * @Author Alex
 * @Date 2019/3/4 14:29
 * @Version 1.0
 **/
public class PraseJsonEntity {
    String name;
    Map<String, Object> objectMap;

    public PraseJsonEntity(String name, JSONObject object) {
        this.name = name;
        this.objectMap = object;
    }

    public PraseJsonEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }
}
