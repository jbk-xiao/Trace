package com.trace.trace.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.entity
 * @Description
 * @create 2021-02-23-23:44
 */
@Data
public class ProvinceIndexData {
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("value")
    private Integer value;
}
