package com.trace.trace.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ProvinceIndexData {
    @Expose(serialize = false, deserialize = false)
    private String period;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("value")
    private Integer value;
}
