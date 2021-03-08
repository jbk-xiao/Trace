package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Detail 商品详情页信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detail {
    private String skuId;
    private int averageScore;
    private int commentCount;
    private int goodCount;
    private double goodRate;
    private int generalCount;
    private double generalRate;
    private int poorCount;
    private double poorRate;
    private String commentStr;
}