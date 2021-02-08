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
 * @create 2021-02-07-11:22
 */
@Data
public class ProcessInfo {
    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("master")
    private String master;

    @Expose
    @SerializedName("time")
    private long time;

    @Expose
    @SerializedName("picture")
    private String picture;

    @Expose
    @SerializedName("procedure")
    private List<ProcedureInfo> procedure;

    public ProcessInfo(String name, String master, long time) {
        this.name = name;
        this.master = master;
        this.time = time;
    }
}