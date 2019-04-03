package com.cmcc.ks.vo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Result
 * @Description TODO
 * @Author Alex
 * @Date 2019/3/14 11:22
 * @Version 1.0
 **/
public class Result {
    private String code;
    private String message;
    private Object data;

    public Result(String code, List<Map<String, Object>> list) {
        this.code = code;
        this.data = list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
