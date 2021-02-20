package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.pojo
 * @Description
 * @create 2021-02-19-20:08
 */
@Data
public class TraceManagerInfo {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("foodName")
    private String foodName;

    @Expose
    @SerializedName("specification")
    private String specification;

    @Expose
    @SerializedName("category")
    private String category;

    @Expose
    @SerializedName("latestProcess")
    private String latestProcess;

    @Expose
    @SerializedName("time")
    private Long time;
}
