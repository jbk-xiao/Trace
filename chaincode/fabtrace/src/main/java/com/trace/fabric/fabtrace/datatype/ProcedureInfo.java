package com.trace.fabric.fabtrace.datatype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author jbk-xiao
 * @program fabtrace
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-02-07-14:53
 */
public final class ProcedureInfo {
    @Expose
    @SerializedName("name")
    private final String name;

    @Expose
    @SerializedName("master")
    private final String master;

    @Expose
    @SerializedName("time")
    private final String time;

    @Expose
    @SerializedName("picture")
    private String picture = "";

    public ProcedureInfo(final String name, final String master, final String time) {
        this.name = name;
        this.master = master;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getMaster() {
        return master;
    }

    public String getTime() {
        return time;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }
}
