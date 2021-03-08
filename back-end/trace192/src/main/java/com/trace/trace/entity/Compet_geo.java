package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 竞品项目地理信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compet_geo implements Serializable {
    private String proj_name;
    private String company_name;
    private String img_url;
    private String region;
    private String lng;
    private String lat;
}
