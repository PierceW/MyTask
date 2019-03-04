package com.alex.test.akazam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JsonUtil
 * @Description TODO
 * @Author Alex
 * @Date 2019/3/4 14:40
 * @Version 1.0
 **/
public class JsonUtil {

    public static List<PraseJsonEntity> parseJson(String element) {
        JSONObject jsonObject = JSON.parseObject(element);
        JSONObject sdkSessionInfo = jsonObject.getJSONObject("sdkSessionInfo");
        JSONArray customInfoArray = jsonObject.getJSONArray("customInfo");
        JSONObject sessionStart = jsonObject.getJSONObject("sessionStart");
        JSONObject sessionEnd = jsonObject.getJSONObject("sessionEnd");
        JSONArray exceptionArray = jsonObject.getJSONArray("exception");

        List<PraseJsonEntity> entityList = new ArrayList<>();
        if (!customInfoArray.isEmpty()) {
            for (int i = 0; i < customInfoArray.size(); i++) {
                JSONObject object = new JSONObject();
                object.putAll(sdkSessionInfo);
                object.putAll(customInfoArray.getJSONObject(i));

                String type = customInfoArray.getJSONObject(i).getString("type");

                entityList.add(new PraseJsonEntity(type, object));
            }
        }

        if (!exceptionArray.isEmpty()) {

        }

        PraseJsonEntity startEntity = compareJson(sessionStart, sdkSessionInfo, "sessionStart");

        if (startEntity != null) {
            entityList.add(startEntity);
        }

        PraseJsonEntity endEntity = compareJson(sessionEnd, sdkSessionInfo, "sessionEnd");

        if (startEntity != null) {
            entityList.add(endEntity);
        }

        return entityList;
    }

    public static PraseJsonEntity compareJson(JSONObject json, JSONObject commonJson, String type) {
        if (!json.isEmpty()) {
            JSONObject object = new JSONObject();
            object.putAll(commonJson);
            object.putAll(json);
            return new PraseJsonEntity(type, object);
        }
        return null;
    }
}
