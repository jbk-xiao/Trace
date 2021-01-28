package com.JingDong.JingDong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Clivia-Han
 * @version 1.0
 * @ClassName Query 商品信息实体类
 * @Description TODO
 * @date 2020/5/24 16:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Query implements Serializable {
    private static final long serialVersionUID = -1840831686851699943L;
    private String skuId;
    private String kind;
    private String title;
    private String shop;
    private String goods_url;
    private String img_url;
    private double price;

}
