package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.entity
 * @Description 商品评论
 * @create 2021-02-08-8:00 下午
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
