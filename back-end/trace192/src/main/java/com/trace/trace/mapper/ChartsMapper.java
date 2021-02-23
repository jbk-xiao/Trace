package com.trace.trace.mapper;

import com.trace.trace.entity.AgeDistributionData;
import com.trace.trace.entity.ProvinceIndexData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.mapper
 * @Description
 * @create 2021-02-23-20:56
 */
@Mapper
@Component
public interface ChartsMapper {
    @Select("SELECT age_range ageRange, tgi, word_rate wordRate, all_rate allRate FROM age_distribution "
            + "WHERE keyword = #{keyword}")
    AgeDistributionData[] selectAgeDistributionData(@Param("keyword") String keyword);

    @Select("SELECT province name, sum_index value FROM province_index WHERE keyword = #{keyword}")
    ProvinceIndexData[] selectProvinceIndexData(@Param("keyword") String keyword);
}
