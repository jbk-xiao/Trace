package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trace.trace.entity.AgeOrSexDistributionData;
import lombok.Data;

/**
 * 性别或年龄分布返回数据对应pojo。（复用）
 *
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.pojo
 * @Description
 * @create 2021-02-23-21:26
 */
@Data
public class AgeOrSexDistribution {
    @Expose
    @SerializedName("keyword")
    private String keyword;
    @Expose
    @SerializedName("period")
    private String period;
    @Expose
    @SerializedName("data")
    private AgeOrSexDistributionData[] ageOrSexDistributionData;

    public AgeOrSexDistribution(String keyword, String period) {
        this.keyword = keyword;
        this.period = period;
    }
}
