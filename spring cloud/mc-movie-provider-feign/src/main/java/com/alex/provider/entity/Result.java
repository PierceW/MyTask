package com.alex.provider.entity;

/**
 * @ClassName Result
 * @Description todo
 * @Author AlexWang
 * @Date 2019/4/26 0026 上午 9:50
 * @Version 1.0
 **/

public class Result {
    private int code;
    private String message;
    private Object data;

    public Result(int code, String success, Object data) {
        this.code = code;
        this.message = success;
        this.data = data;
    }

    public static Result error(Throwable throwable) {
        return new Result(300, throwable.getMessage(), null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }
}
