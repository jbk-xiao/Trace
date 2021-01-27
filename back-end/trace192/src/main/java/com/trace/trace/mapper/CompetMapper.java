package com.trace.trace.mapper;

import com.trace.trace.entity.Compet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     *
     * @param compet_regis_id
     * @return Compet
     */
    List<Compet> selectCompetByCompany(@Param("compet_regis_id") String compet_regis_id);

}
