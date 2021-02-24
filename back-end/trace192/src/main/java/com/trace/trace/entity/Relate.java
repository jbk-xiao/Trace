package com.trace.trace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.entity
 * @Description
 * @create 2021-02-23-15:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Relate {
    private String keyword;
    private String word;
    private int pv;
    private float ratio;
    private String period;
}
