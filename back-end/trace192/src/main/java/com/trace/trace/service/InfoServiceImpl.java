package com.trace.trace.service;

import com.trace.trace.entity.Compet;
import com.trace.trace.grpc.QueryRequest;
import com.trace.trace.grpc.QueryResponse;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.mapper.CompetMapper;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace.service
 * @Description
 * @create 2021-01-26-5:18 下午
 */
@GRpcService
@Slf4j
@Service
public class InfoServiceImpl extends SearchServiceGrpc.SearchServiceImplBase{
    @Autowired
    private CompetMapper competMapper;

    @Override
    public void searchQuery(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
        String company = request.getQuery();
        log.info("Receive query = "+company);
        List<Compet> compets= competMapper.selectCompetByCompany(company);
        //把结果放入response
        QueryResponse response = QueryResponse.newBuilder().setResponse(compets.toString()).build();
        //放入response，传回客户端
        responseObserver.onNext(response);
        //表示此次连接结束
        responseObserver.onCompleted();
    }
}
