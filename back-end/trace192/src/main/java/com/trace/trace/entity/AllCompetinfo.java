package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 所有竞品模块返回的整合信息
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
