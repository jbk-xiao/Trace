package com.trace.trace.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.controller
 * @Description
 * @create 2021-02-01-0:07
 */
class SaveMediaControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

//    private final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new SaveMediaController()).build();

    @Test
    public void saveMedia() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8290/saveName/test.mp4");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andDo(MockMvcResultHandlers.print());
    }
}