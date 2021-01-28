package com.JingDong.JingDong.mapper;

import com.JingDong.JingDong.entity.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QueryMapper {
    /**
     *
     * @param skuIds
     * @return
     */
    List<Query> selectQueryBySkuIds(@Param("skuIds")List<String> skuIds);

    /**
     *
     * @param skuId
     * @return Query
     */
    @Select("SELECT * FROM load_test WHERE skuId = #{skuId}")
    List<Query> selectQueryBySkuId(@Param("skuIds") String skuId);

}
