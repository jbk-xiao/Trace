package com.JingDong.JingDong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Clivia-Han
 * @version 1.0
 * @ClassName Detail 商品详情页信息
 * @Description TODO
 * @date 2020/5/24 16:11
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