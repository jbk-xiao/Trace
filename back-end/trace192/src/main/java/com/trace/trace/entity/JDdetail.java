package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDdetail {
    private String pname;
    private String price;
    private String detail_url;
    private String img_url;
    private String brand;
    private String brand_url;
    private int commentCount;
    private double goodRate;
}
