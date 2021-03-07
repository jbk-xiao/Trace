package com.trace.fabric.fabtrace.datatype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hyperledger.fabric.contract.annotation.DataType;

import java.util.ArrayList;

/**
 * @author jbk-xiao
 * @program fabtrace
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-02-04-20:07
 */
@DataType
public final class TraceInfo {
    @Expose
    @SerializedName("id")
    private final String id;

    @Expose
    @SerializedName("com")
    private final String com;

    @Expose
    @SerializedName("foodName")
    private final String foodName;

    @Expose
    @SerializedName("specification")
    private final String specification;

    @Expose
    @SerializedName("category")
    private final String category;

    @Expose
    @SerializedName("processCount")
    private final Integer processCount;

    @Expose
    @SerializedName("process")
    private final ArrayList<ProcessInfo> process = new ArrayList<>();

    public TraceInfo(final String id, final String com, final String foodName, final String specification,
                     final String category, final Integer processCount) {
        this.id = id;
        this.com = com;
        this.foodName = foodName;
        this.specification = specification;
        this.category = category;
        this.processCount = processCount;
    }

    public void addProcessInfo(final String name, final String master, final String time, final String location) {
        this.process.add(new ProcessInfo(name, master, time, location));
    }

    public String getId() {
        return id;
    }

    public String getCom() {
        return com;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getSpecification() {
        return specification;
    }

    public String getCategory() {
        return category;
    }

    public Integer getProcessCount() {
        return processCount;
    }

    public ArrayList<ProcessInfo> getProcess() {
        return process;
    }

    @Override
    public String toString() {
        return "TraceInfo{"
               + "id='" + id + '\''
               + ", process=" + process
               + '}';
    }
}
