package com.trace.trace.config;

import com.trace.trace.grpc.ManagerServiceGrpc;
import com.trace.trace.grpc.SearchChartsServiceGrpc;
import com.trace.trace.grpc.SearchServiceGrpc;
import com.trace.trace.grpc.SearchTraceServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
    /**
     * 配置需要连接的server的ip:port
     */
    @Bean
    ManagedChannel channel(@Value("${app-config.grpc-server-name}") String name,
                           @Value("${app-config.grpc-server-port}") Integer port,
                           @Value("${app-config.max-message-size}") Integer size) {
        return ManagedChannelBuilder.forAddress(name, port).maxInboundMessageSize(size)
                .usePlaintext()
                .build();
    }

    /**
     * 将proto生成的stub放入容器中，方便调用
     */
    @Bean
    SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub(ManagedChannel channel) {
        return SearchServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    SearchTraceServiceGrpc.SearchTraceServiceBlockingStub searchTraceServiceBlockingStub(ManagedChannel channel) {
        return SearchTraceServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    SearchChartsServiceGrpc.SearchChartsServiceBlockingStub searchChartsServiceBlockingStub(ManagedChannel channel) {
        return SearchChartsServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    ManagerServiceGrpc.ManagerServiceBlockingStub managerServiceBlockingStub(ManagedChannel channel) {
        return ManagerServiceGrpc.newBlockingStub(channel);
    }
}