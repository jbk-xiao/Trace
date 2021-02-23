package com.trace.trace.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.entity
 * @Description 年龄分布返回值列表内每个数据。
 * @create 2021-02-23-20:55
 */
@Data
public class AgeDistributionData {
    @Expose
    @SerializedName("age_range")
    private String ageRange;
    @Expose
    @SerializedName("tgi")
    private Double tgi;
    @Expose
    @SerializedName("word_rate")
    private Double wordRate;
    @Expose
    @SerializedName("all_rate")
    private Double allRate;
}
