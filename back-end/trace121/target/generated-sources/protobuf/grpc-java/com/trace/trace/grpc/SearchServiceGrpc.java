package com.trace.trace.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.23.0)",
    comments = "Source: SearchService.proto")
public final class SearchServiceGrpc {

  private SearchServiceGrpc() {}

  public static final String SERVICE_NAME = "com.trace.trace.grpc.SearchService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.trace.trace.grpc.QueryRequest,
      com.trace.trace.grpc.QueryResponse> getSearchQueryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchQuery",
      requestType = com.trace.trace.grpc.QueryRequest.class,
      responseType = com.trace.trace.grpc.QueryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trace.trace.grpc.QueryRequest,
      com.trace.trace.grpc.QueryResponse> getSearchQueryMethod() {
    io.grpc.MethodDescriptor<com.trace.trace.grpc.QueryRequest, com.trace.trace.grpc.QueryResponse> getSearchQueryMethod;
    if ((getSearchQueryMethod = SearchServiceGrpc.getSearchQueryMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getSearchQueryMethod = SearchServiceGrpc.getSearchQueryMethod) == null) {
          SearchServiceGrpc.getSearchQueryMethod = getSearchQueryMethod =
              io.grpc.MethodDescriptor.<com.trace.trace.grpc.QueryRequest, com.trace.trace.grpc.QueryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchQuery"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.QueryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.QueryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("searchQuery"))
              .build();
        }
      }
    }
    return getSearchQueryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trace.trace.grpc.CompetRequest,
      com.trace.trace.grpc.QueryResponse> getSearchCompetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchCompet",
      requestType = com.trace.trace.grpc.CompetRequest.class,
      responseType = com.trace.trace.grpc.QueryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trace.trace.grpc.CompetRequest,
      com.trace.trace.grpc.QueryResponse> getSearchCompetMethod() {
    io.grpc.MethodDescriptor<com.trace.trace.grpc.CompetRequest, com.trace.trace.grpc.QueryResponse> getSearchCompetMethod;
    if ((getSearchCompetMethod = SearchServiceGrpc.getSearchCompetMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getSearchCompetMethod = SearchServiceGrpc.getSearchCompetMethod) == null) {
          SearchServiceGrpc.getSearchCompetMethod = getSearchCompetMethod =
              io.grpc.MethodDescriptor.<com.trace.trace.grpc.CompetRequest, com.trace.trace.grpc.QueryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchCompet"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.CompetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.QueryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("searchCompet"))
              .build();
        }
      }
    }
    return getSearchCompetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trace.trace.grpc.QueryRequest,
      com.trace.trace.grpc.TraceResponse> getSearchTraceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchTrace",
      requestType = com.trace.trace.grpc.QueryRequest.class,
      responseType = com.trace.trace.grpc.TraceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trace.trace.grpc.QueryRequest,
      com.trace.trace.grpc.TraceResponse> getSearchTraceMethod() {
    io.grpc.MethodDescriptor<com.trace.trace.grpc.QueryRequest, com.trace.trace.grpc.TraceResponse> getSearchTraceMethod;
    if ((getSearchTraceMethod = SearchServiceGrpc.getSearchTraceMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getSearchTraceMethod = SearchServiceGrpc.getSearchTraceMethod) == null) {
          SearchServiceGrpc.getSearchTraceMethod = getSearchTraceMethod =
              io.grpc.MethodDescriptor.<com.trace.trace.grpc.QueryRequest, com.trace.trace.grpc.TraceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchTrace"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.QueryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.TraceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("searchTrace"))
              .build();
        }
      }
    }
    return getSearchTraceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trace.trace.grpc.GraphRequestByKind,
      com.trace.trace.grpc.GraphResponseByKind> getSearchGraphByKindMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchGraphByKind",
      requestType = com.trace.trace.grpc.GraphRequestByKind.class,
      responseType = com.trace.trace.grpc.GraphResponseByKind.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trace.trace.grpc.GraphRequestByKind,
      com.trace.trace.grpc.GraphResponseByKind> getSearchGraphByKindMethod() {
    io.grpc.MethodDescriptor<com.trace.trace.grpc.GraphRequestByKind, com.trace.trace.grpc.GraphResponseByKind> getSearchGraphByKindMethod;
    if ((getSearchGraphByKindMethod = SearchServiceGrpc.getSearchGraphByKindMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getSearchGraphByKindMethod = SearchServiceGrpc.getSearchGraphByKindMethod) == null) {
          SearchServiceGrpc.getSearchGraphByKindMethod = getSearchGraphByKindMethod =
              io.grpc.MethodDescriptor.<com.trace.trace.grpc.GraphRequestByKind, com.trace.trace.grpc.GraphResponseByKind>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchGraphByKind"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.GraphRequestByKind.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.GraphResponseByKind.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("searchGraphByKind"))
              .build();
        }
      }
    }
    return getSearchGraphByKindMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.trace.trace.grpc.GraphRequestByBrand,
      com.trace.trace.grpc.GraphResponseByBrand> getSearchGraphByBrandMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchGraphByBrand",
      requestType = com.trace.trace.grpc.GraphRequestByBrand.class,
      responseType = com.trace.trace.grpc.GraphResponseByBrand.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.trace.trace.grpc.GraphRequestByBrand,
      com.trace.trace.grpc.GraphResponseByBrand> getSearchGraphByBrandMethod() {
    io.grpc.MethodDescriptor<com.trace.trace.grpc.GraphRequestByBrand, com.trace.trace.grpc.GraphResponseByBrand> getSearchGraphByBrandMethod;
    if ((getSearchGraphByBrandMethod = SearchServiceGrpc.getSearchGraphByBrandMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getSearchGraphByBrandMethod = SearchServiceGrpc.getSearchGraphByBrandMethod) == null) {
          SearchServiceGrpc.getSearchGraphByBrandMethod = getSearchGraphByBrandMethod =
              io.grpc.MethodDescriptor.<com.trace.trace.grpc.GraphRequestByBrand, com.trace.trace.grpc.GraphResponseByBrand>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchGraphByBrand"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.GraphRequestByBrand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.trace.trace.grpc.GraphResponseByBrand.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("searchGraphByBrand"))
              .build();
        }
      }
    }
    return getSearchGraphByBrandMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SearchServiceStub newStub(io.grpc.Channel channel) {
    return new SearchServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SearchServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SearchServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SearchServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SearchServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SearchServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void searchQuery(com.trace.trace.grpc.QueryRequest request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.QueryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSearchQueryMethod(), responseObserver);
    }

    /**
     */
    public void searchCompet(com.trace.trace.grpc.CompetRequest request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.QueryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSearchCompetMethod(), responseObserver);
    }

    /**
     */
    public void searchTrace(com.trace.trace.grpc.QueryRequest request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.TraceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSearchTraceMethod(), responseObserver);
    }

    /**
     */
    public void searchGraphByKind(com.trace.trace.grpc.GraphRequestByKind request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.GraphResponseByKind> responseObserver) {
      asyncUnimplementedUnaryCall(getSearchGraphByKindMethod(), responseObserver);
    }

    /**
     */
    public void searchGraphByBrand(com.trace.trace.grpc.GraphRequestByBrand request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.GraphResponseByBrand> responseObserver) {
      asyncUnimplementedUnaryCall(getSearchGraphByBrandMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSearchQueryMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.trace.trace.grpc.QueryRequest,
                com.trace.trace.grpc.QueryResponse>(
                  this, METHODID_SEARCH_QUERY)))
          .addMethod(
            getSearchCompetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.trace.trace.grpc.CompetRequest,
                com.trace.trace.grpc.QueryResponse>(
                  this, METHODID_SEARCH_COMPET)))
          .addMethod(
            getSearchTraceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.trace.trace.grpc.QueryRequest,
                com.trace.trace.grpc.TraceResponse>(
                  this, METHODID_SEARCH_TRACE)))
          .addMethod(
            getSearchGraphByKindMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.trace.trace.grpc.GraphRequestByKind,
                com.trace.trace.grpc.GraphResponseByKind>(
                  this, METHODID_SEARCH_GRAPH_BY_KIND)))
          .addMethod(
            getSearchGraphByBrandMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.trace.trace.grpc.GraphRequestByBrand,
                com.trace.trace.grpc.GraphResponseByBrand>(
                  this, METHODID_SEARCH_GRAPH_BY_BRAND)))
          .build();
    }
  }

  /**
   */
  public static final class SearchServiceStub extends io.grpc.stub.AbstractStub<SearchServiceStub> {
    private SearchServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchServiceStub(channel, callOptions);
    }

    /**
     */
    public void searchQuery(com.trace.trace.grpc.QueryRequest request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.QueryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSearchQueryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchCompet(com.trace.trace.grpc.CompetRequest request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.QueryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSearchCompetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchTrace(com.trace.trace.grpc.QueryRequest request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.TraceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSearchTraceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchGraphByKind(com.trace.trace.grpc.GraphRequestByKind request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.GraphResponseByKind> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSearchGraphByKindMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchGraphByBrand(com.trace.trace.grpc.GraphRequestByBrand request,
        io.grpc.stub.StreamObserver<com.trace.trace.grpc.GraphResponseByBrand> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSearchGraphByBrandMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SearchServiceBlockingStub extends io.grpc.stub.AbstractStub<SearchServiceBlockingStub> {
    private SearchServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.trace.trace.grpc.QueryResponse searchQuery(com.trace.trace.grpc.QueryRequest request) {
      return blockingUnaryCall(
          getChannel(), getSearchQueryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.trace.trace.grpc.QueryResponse searchCompet(com.trace.trace.grpc.CompetRequest request) {
      return blockingUnaryCall(
          getChannel(), getSearchCompetMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.trace.trace.grpc.TraceResponse searchTrace(com.trace.trace.grpc.QueryRequest request) {
      return blockingUnaryCall(
          getChannel(), getSearchTraceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.trace.trace.grpc.GraphResponseByKind searchGraphByKind(com.trace.trace.grpc.GraphRequestByKind request) {
      return blockingUnaryCall(
          getChannel(), getSearchGraphByKindMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.trace.trace.grpc.GraphResponseByBrand searchGraphByBrand(com.trace.trace.grpc.GraphRequestByBrand request) {
      return blockingUnaryCall(
          getChannel(), getSearchGraphByBrandMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SearchServiceFutureStub extends io.grpc.stub.AbstractStub<SearchServiceFutureStub> {
    private SearchServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trace.trace.grpc.QueryResponse> searchQuery(
        com.trace.trace.grpc.QueryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSearchQueryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trace.trace.grpc.QueryResponse> searchCompet(
        com.trace.trace.grpc.CompetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSearchCompetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trace.trace.grpc.TraceResponse> searchTrace(
        com.trace.trace.grpc.QueryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSearchTraceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trace.trace.grpc.GraphResponseByKind> searchGraphByKind(
        com.trace.trace.grpc.GraphRequestByKind request) {
      return futureUnaryCall(
          getChannel().newCall(getSearchGraphByKindMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.trace.trace.grpc.GraphResponseByBrand> searchGraphByBrand(
        com.trace.trace.grpc.GraphRequestByBrand request) {
      return futureUnaryCall(
          getChannel().newCall(getSearchGraphByBrandMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEARCH_QUERY = 0;
  private static final int METHODID_SEARCH_COMPET = 1;
  private static final int METHODID_SEARCH_TRACE = 2;
  private static final int METHODID_SEARCH_GRAPH_BY_KIND = 3;
  private static final int METHODID_SEARCH_GRAPH_BY_BRAND = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SearchServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SearchServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEARCH_QUERY:
          serviceImpl.searchQuery((com.trace.trace.grpc.QueryRequest) request,
              (io.grpc.stub.StreamObserver<com.trace.trace.grpc.QueryResponse>) responseObserver);
          break;
        case METHODID_SEARCH_COMPET:
          serviceImpl.searchCompet((com.trace.trace.grpc.CompetRequest) request,
              (io.grpc.stub.StreamObserver<com.trace.trace.grpc.QueryResponse>) responseObserver);
          break;
        case METHODID_SEARCH_TRACE:
          serviceImpl.searchTrace((com.trace.trace.grpc.QueryRequest) request,
              (io.grpc.stub.StreamObserver<com.trace.trace.grpc.TraceResponse>) responseObserver);
          break;
        case METHODID_SEARCH_GRAPH_BY_KIND:
          serviceImpl.searchGraphByKind((com.trace.trace.grpc.GraphRequestByKind) request,
              (io.grpc.stub.StreamObserver<com.trace.trace.grpc.GraphResponseByKind>) responseObserver);
          break;
        case METHODID_SEARCH_GRAPH_BY_BRAND:
          serviceImpl.searchGraphByBrand((com.trace.trace.grpc.GraphRequestByBrand) request,
              (io.grpc.stub.StreamObserver<com.trace.trace.grpc.GraphResponseByBrand>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SearchServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SearchServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.trace.trace.grpc.SearchServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SearchService");
    }
  }

  private static final class SearchServiceFileDescriptorSupplier
      extends SearchServiceBaseDescriptorSupplier {
    SearchServiceFileDescriptorSupplier() {}
  }

  private static final class SearchServiceMethodDescriptorSupplier
      extends SearchServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SearchServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SearchServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SearchServiceFileDescriptorSupplier())
              .addMethod(getSearchQueryMethod())
              .addMethod(getSearchCompetMethod())
              .addMethod(getSearchTraceMethod())
              .addMethod(getSearchGraphByKindMethod())
              .addMethod(getSearchGraphByBrandMethod())
              .build();
        }
      }
    }
    return result;
  }
}
