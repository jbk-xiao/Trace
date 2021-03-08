package com.trace.fabric.fabtrace.datatype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hyperledger.fabric.contract.annotation.DataType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jbk-xiao
 * @program fabtrace
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-02-07-11:58
 */
@DataType
public final class ProcessInfo {
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
    @SerializedName("location")
    private final String location;

    @Expose
    @SerializedName("picture")
    private String picture = "";

    @Expose
    @SerializedName("procedure")
    private final List<ProcedureInfo> procedure = new ArrayList<>();

    ProcessInfo(final String name, final String master, final String time, final String location) {
        this.name = name;
        this.master = master;
        this.time = time;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }

    public List<ProcedureInfo> getProcedure() {
        return procedure;
    }

    public void addProcedureInfo(final String pname, final String pmaster, final String ptime) {
        this.procedure.add(new ProcedureInfo(pname, pmaster, ptime));
    }

    @Override
    public String toString() {
        return "ProcessInfo{"
               + "name='" + name + '\''
               + ", master='" + master + '\''
               + ", time='" + time + '\''
               + ", picture='" + picture + '\''
               + '}';
    }
}
