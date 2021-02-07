package com.trace.trace.service;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.trace.trace.dao.FabricDao;
import com.trace.trace.dao.ProcessEventDao;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.grpc.TraceResponse;
import com.trace.trace.util.createJson;
import com.trace.trace.util.media.FileUtil;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zenglr
 * @program: FoXiShengCun
 * @packagename: com.trace.trace.service
 * @Description 溯源模块
 * @create 2021-02-07-7:57 下午
 */
@Component
@Slf4j
public class SearchTrace {

    private final Gson gson = new Gson();

    @Autowired
    private ProcessEventDao processEventDao;

    @Autowired
    private FabricDao fabricDao;

    @Autowired
    private FileUtil fileUtil;

    private final createJson json = new createJson();


}
