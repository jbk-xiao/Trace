package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace.entity
 * @Description 竞品项目信息
 * @create 2021-01-26-5:05 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compet implements Serializable {
    private String regis_id;
    private String proj_name;
    private String es_time;
    private String region;
    private String company_name;
    private String proj_desc;
    private String address;
    private String lng;
    private String lat;
    private String Regis_capital;
    private String org_code;
    private String phone_num;
}
