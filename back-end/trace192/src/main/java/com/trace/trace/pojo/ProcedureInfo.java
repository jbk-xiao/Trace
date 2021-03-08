package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ProcedureInfo {
    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("master")
    private String master;

    @Expose
    @SerializedName("time")
    private String time;

    @Expose
    @SerializedName("picture")
    private String picture;

    public ProcedureInfo(String name, String master, String time) {
        this.name = name;
        this.master = master;
        this.time = time;
    }
}