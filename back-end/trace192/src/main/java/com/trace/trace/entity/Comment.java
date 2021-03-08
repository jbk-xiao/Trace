package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comment 商品评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private String skuId;
    private String userId;
    private String productId;
    private String productClass;
    private int score;
    private String comment;
}
