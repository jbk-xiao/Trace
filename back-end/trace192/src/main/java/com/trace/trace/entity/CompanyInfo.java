package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 公司基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInfo implements Serializable {
    private String proj_name;
    private String img_url;
    private String es_time;
    private String region;
    private String company_name;
    private String proj_desc;
    private String address;
    private String lng;
    private String lat;
    private String phone_num;

}
