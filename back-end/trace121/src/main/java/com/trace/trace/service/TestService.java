package com.trace.trace.service;

/**
 * @author Zenglr
 * @program: trace
 * @packagename: com.trace.trace.service
 * @Description
 * @create 2021-01-26-4:13 下午
 */
//@GRpcService
//@Slf4j
//@Service
//public class TestService extends SearchServiceGrpc.SearchServiceImplBase{
//
//    @Override
//    public void searchQuery(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
//        String company = request.getQuery();
//        log.info("Receive query = "+company);
//
//        //把结果放入response
//        QueryResponse response = QueryResponse.newBuilder().setResponse(company).build();
//        //放入response，传回客户端
//        responseObserver.onNext(response);
//        //表示此次连接结束
//        responseObserver.onCompleted();
//    }
//}