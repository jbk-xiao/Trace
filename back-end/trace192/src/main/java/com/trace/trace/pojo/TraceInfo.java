package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.pojo
 * @Description
 * @create 2021-02-07-11:32
 */
@Data
public class TraceInfo {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("process")
    private List<ProcessInfo> process;

    public TraceInfo(String id) {
        this.id = id;
    }

}
