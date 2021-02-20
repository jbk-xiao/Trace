package com.trace.trace.util;

import com.google.gson.Gson;
import com.trace.trace.entity.Detail;
import com.trace.trace.entity.Query;

import java.util.List;

/**
 * 生成json用的
 */
public class createJson {
    private Gson gson = new Gson();

    public  String toJson(List list){
        return gson.toJson(list);
    }

    /**
     * 为请求类型为keyword的结果生成JSON
     * @param pageCount
     * @param queries
     * @return
     */
    public <T> String toJson(String pageCount, List<T> queries){
        String jsonInfo=toJson(queries),
                jsonPageCount="[{\"pageCount\":\""+pageCount+"\"}";
        if(queries.size()==0) jsonInfo=jsonPageCount+"]";
        else jsonInfo=jsonPageCount+","+jsonInfo.substring(1);
        return jsonInfo;
    }

}
