package com.trace.trace.mapper;

import com.trace.trace.entity.Detail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DetailMapper {
    /**
     * @param skuId
     * @return Detail
     */
    @Select("select * from load_test2 where skuId=#{skuId}")
    Detail selectDetailBySkuId(@Param("skuId") String skuId);

//    List<Detail> selectDetailBySkuId(@Param("skuId")String skuId);

}
