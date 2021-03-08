package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment_score {
    private String brand;
    private String package_score;
    private String price_score;
    private String logistics_score;
    private String taste_score;
    private String service_score;
}
