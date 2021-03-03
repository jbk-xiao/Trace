package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.entity
 * @Description
 * [
 *     ["brand","comment_score","count","sku_id","price"],
 *     ["五芳斋",0.73943662,51,"100001559639",26.8],
 *     ["亨氏",0.707746479,92,"2169939",9.9]
 * ]
 * @create 2021-03-02-23:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class S3dScore {
    private String brand;
    private Double commentScore;
    private Integer count;
    private String skuId;
    private Double price;

    @Override
    public String toString() {
        return "["
               + '\"' + brand + '"'
               + "," + commentScore
               + "," + count
               + ",\"" + skuId + '"'
               + "," + price
               + ']';
    }
}
