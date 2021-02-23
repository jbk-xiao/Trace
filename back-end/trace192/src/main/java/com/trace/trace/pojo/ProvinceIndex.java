package com.trace.trace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trace.trace.entity.ProvinceIndexData;
import lombok.Data;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.pojo
 * @Description
 * @create 2021-02-24-0:10
 */
@Data
public class ProvinceIndex {
    @Expose
    @SerializedName("keyword")
    private String keyword;
    @Expose
    @SerializedName("period")
    private String period;
    @Expose
    @SerializedName("data")
    private ProvinceIndexData[] provinceIndexData;

    public ProvinceIndex(String keyword) {
        this.keyword = keyword;
    }
}
