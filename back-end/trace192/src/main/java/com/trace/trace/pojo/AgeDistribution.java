package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trace.trace.entity.AgeDistributionData;
import lombok.Data;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.pojo
 * @Description
 * @create 2021-02-23-21:26
 */
@Data
public class AgeDistribution {
    @Expose
    @SerializedName("keyword")
    private String keyword;
    @Expose
    @SerializedName("period")
    private String period;
    @Expose
    @SerializedName("data")
    private AgeDistributionData[] ageDistributionData;
    public AgeDistribution() {

    }
    public AgeDistribution(String keyword) {
        this.keyword = keyword;
    }
}
