package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trace.trace.entity.CompanyInfo;
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
    @SerializedName("foodName")
    private String foodName;

    @Expose
    @SerializedName("specification")
    private String specification;

    @Expose
    @SerializedName("category")
    private String category;

    @Expose
    @SerializedName("processCount")
    private Integer processCount;

    @Expose
    @SerializedName("com")
    private String com;

    @Expose
    @SerializedName("company_info")
    private CompanyInfo companyInfo;
//    @Expose
//    @SerializedName("address")
//    private String address;
//
//    @Expose
//    @SerializedName("contact")
//    private String contact;
//
//    @Expose
//    @SerializedName("description")
//    private String description;

    @Expose
    @SerializedName("qrCode")
    private String qrCode;

    @Expose
    @SerializedName("process")
    private List<ProcessInfo> process;

    public TraceInfo(String id) {
        this.id = id;
    }

}
