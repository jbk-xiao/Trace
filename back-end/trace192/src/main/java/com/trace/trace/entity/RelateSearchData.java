package com.trace.trace.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.entity
 * @Description
 * @create 2021-03-02-20:01
 */
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
