package com.trace.trace.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class RelateSearchData {
    @Expose(serialize = false, deserialize = false)
    private String period;
    @Expose
    @SerializedName("word")
    private String word;
    @Expose
    @SerializedName("pv")
    private Integer pv;
    @Expose
    @SerializedName("ratio")
    private Double ratio;
}
