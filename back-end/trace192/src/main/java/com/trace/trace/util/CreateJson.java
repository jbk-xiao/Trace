package com.trace.trace.util;

import com.google.gson.Gson;

import java.util.List;

/**
 * 生成json用的
 */
public class CreateJson {
    private final Gson gson = new Gson();

    public <T> String toJson(List<T> list) {
        return gson.toJson(list);
    }

    /**
     * 为请求类型为keyword的结果生成JSON
     *
     * @param pageCount
     * @param queries
     * @return
     */
    public <T> String toJson(String pageCount, List<T> queries) {
        String jsonInfo = toJson(queries),
                jsonPageCount = "[{\"pageCount\":\"" + pageCount + "\"}";
        if (queries.size() == 0) jsonInfo = jsonPageCount + "]";
        else jsonInfo = jsonPageCount + "," + jsonInfo.substring(1);
        return jsonInfo;
    }

}
