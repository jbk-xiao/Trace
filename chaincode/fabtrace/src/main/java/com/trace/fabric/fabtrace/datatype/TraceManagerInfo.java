package com.trace.fabric.fabtrace.datatype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author jbk-xiao
 * @program fabtrace
 * @packagename com.trace.fabric.fabtrace.datatype
 * @Description
 * @create 2021-02-19-20:25
 */
public final class TraceManagerInfo {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("latestProcess")
    private String latestProcess;

    @Expose
    @SerializedName("time")
    private String time;

    public TraceManagerInfo(final String id, final String latestProcess, final String time) {
        this.id = id;
        this.latestProcess = latestProcess;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLatestProcess() {
        return latestProcess;
    }

    public void setLatestProcess(final String latestProcess) {
        this.latestProcess = latestProcess;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }
}
