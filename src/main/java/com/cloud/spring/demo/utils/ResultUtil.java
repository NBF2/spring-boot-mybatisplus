package com.cloud.spring.demo.utils;

import com.alibaba.fastjson.JSONObject;

public class ResultUtil {

    public static JSONObject getResult(Object data, Boolean status, String info, Integer errorCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);
        jsonObject.put("status", status);
        jsonObject.put("msg", info);
        jsonObject.put("errorCode", errorCode);

        return jsonObject;
    }

    public static JSONObject data(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);
        jsonObject.put("status", true);
        jsonObject.put("msg","操作成功");

        return jsonObject;
    }

    public static JSONObject fail(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", "");
        jsonObject.put("status", false);
        jsonObject.put("msg", msg);

        return jsonObject;
    }
}
