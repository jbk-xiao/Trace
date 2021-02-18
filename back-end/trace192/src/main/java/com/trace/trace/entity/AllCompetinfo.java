package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace.entity
 * @Description 所有竞品模块返回的整合信息
 * @create 2021-01-26-11:17 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllCompetinfo implements Serializable {
    private CompanyInfo companyInfo;
    private List<Compet_geo> compet_geoList;
    private JDdetail jdetail;
    private List<JDdetail> compet_jdetails;
    private List<Comment_score> scoreList;
}
