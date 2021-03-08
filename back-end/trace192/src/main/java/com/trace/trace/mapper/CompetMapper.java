package com.trace.trace.mapper;

import com.trace.trace.entity.Comment_score;
import com.trace.trace.entity.CompanyInfo;
import com.trace.trace.entity.Compet_geo;
import com.trace.trace.entity.JDdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CompetMapper {


    /**
     * 根据公司id获取到公司的基本信息
     *
     * @param regis_id
     * @return
     */
    @Select("SELECT proj_name,img_url,es_time,region,company_name,proj_desc,address,lng,lat,phone_num FROM company WHERE regis_id = #{regis_id}")
    CompanyInfo selectCompanyBasicInfo(@Param("regis_id") String regis_id);

    /**
     * 根据skuIds获取到商品信息
     *
     * @param skuIds
     * @return
     */
    List<JDdetail> selectCompetBySkuIds(@Param("skuIds") List<String> skuIds);

    /**
     * 根据skuId获取到商品信息
     *
     * @param skuId
     * @return
     */
    @Select("select pname,price,detail_url,img_url,brand,brand_url,commentCount,goodRate from jd_info where sku_id = #{skuId}")
    JDdetail selectInfoBySkuId(@Param("skuId") String skuId);

    /**
     * 根据regisids获取竞品项目地理信息
     *
     * @param regisIds
     * @return
     */
    List<Compet_geo> selectCompetByRegisIds(@Param("regisIds") List<String> regisIds);

    /**
     * 根据skuIds获取到对应商品以及竞品的评论维度分数
     *
     * @param skuIds
     * @return
     */
    List<Comment_score> selectCommentScoreBySkuIds(@Param("skuIds") List<String> skuIds);


}
