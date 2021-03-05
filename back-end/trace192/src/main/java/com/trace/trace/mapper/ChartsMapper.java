package com.trace.trace.mapper;

import com.trace.trace.entity.AgeOrSexDistributionData;
import com.trace.trace.entity.ProvinceIndexData;
import com.trace.trace.entity.RelateSearchData;
import com.trace.trace.entity.S3dScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.mapper
 * @Description
 * @create 2021-02-23-20:56
 */
@Mapper
@Repository
public interface ChartsMapper {
    @Select("SELECT period, age_range `range`, tgi, word_rate wordRate, all_rate allRate FROM age_distribution "
            + "WHERE keyword = #{keyword}")
    AgeOrSexDistributionData[] selectAgeDistributionData(@Param("keyword") String keyword);

    @Select("SELECT period, sex_range `range`, tgi, word_rate wordRate, all_rate allRate FROM sex_distribution "
            + "WHERE keyword = #{keyword}")
    AgeOrSexDistributionData[] selectSexDistributionData(String keyword);

    @Select("SELECT period, province name, sum_index value FROM province_index WHERE keyword = #{keyword}")
    ProvinceIndexData[] selectProvinceIndexData(@Param("keyword") String keyword);

    @Select("SELECT period, word, pv, ratio FROM relate_search WHERE keyword = #{keyword}")
    RelateSearchData[] selectRelateSearch(@Param("keyword") String keyword);

    S3dScore[] select3dScore(@Param("skuIds") Set<String> skuIds);
}
