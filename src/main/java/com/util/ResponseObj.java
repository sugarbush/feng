package com.util;

/**
 * @Author: feng
 * @Date: 2019/9/18 17:11
 */
public class ResponseObj {
    private int code ;
    private String msg;

    public ResponseObj() {
    }

    public ResponseObj(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
