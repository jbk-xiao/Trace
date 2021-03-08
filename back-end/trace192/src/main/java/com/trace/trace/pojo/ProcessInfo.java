package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ProcessInfo {
    @Expose
    @SerializedName("location")
    private String location;
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

    @Expose
    @SerializedName("procedure")
    private List<ProcedureInfo> procedure;

    public ProcessInfo(String name, String master, String time, String location) {
        this.name = name;
        this.master = master;
        this.time = time;
        this.location = location;
    }
}