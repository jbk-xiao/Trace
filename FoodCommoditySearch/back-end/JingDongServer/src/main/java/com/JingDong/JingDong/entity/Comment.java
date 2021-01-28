package com.JingDong.JingDong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Clivia-Han
 * @version 1.0
 * @ClassName Comment 商品评论
 * @Description TODO
 * @date 2020/5/24 16:11
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
