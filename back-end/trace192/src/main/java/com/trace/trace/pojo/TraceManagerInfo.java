package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

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
    private String time;
}
