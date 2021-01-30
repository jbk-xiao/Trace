package com.trace.trace.mapper;

import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.JDdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace.mapper
 * @Description
 * @create 2021-01-26-5:09 下午
 */
@Mapper
@Component
public interface CompetMapper {


    /**
     * 根据主公司id获取竞品项目地理信息
     * @param regis_id
     * @return Compet
     */
    List<Compet_geo> selectCompetByCompany(@Param("regis_id") String regis_id);

    /**
     * 根据公司id获取到公司的基本信息
     * @param regis_id
     * @return
     */
    @Select("SELECT proj_name,img_url,es_time,region,company_name,proj_desc,address,lng,lat,phone_num FROM company WHERE regis_id = #{regis_id}")
    CompanyInfo selectCompanyBasicInfo(@Param("regis_id") String regis_id);

    /**
     * 根据公司id获取到公司对应的京东商品基本信息
     * @param regis_id
     * @return
     */
    JDdetail selectMainDetail(@Param("regis_id") String regis_id);

    /**
     * 根据公司id获取到公司对应的京东商品竞品基本信息
     * @param regis_id
     * @return
     */
    List<JDdetail> selectCompetDetails(@Param("regis_id") String regis_id);

}
