package io.grpc.SistemasDistruidos.message;

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
 * <pre>
 * Defining a Service, a Service can have multiple RPC operations
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.11.0)",
    comments = "Source: message.proto")
public final class ComandServiceGrpc {

  private ComandServiceGrpc() {}

  public static final String SERVICE_NAME = "message.ComandService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCmdMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.SistemasDistruidos.message.ComandRequest,
      io.grpc.SistemasDistruidos.message.ComandResponse> METHOD_CMD = getCmdMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.SistemasDistruidos.message.ComandRequest,
      io.grpc.SistemasDistruidos.message.ComandResponse> getCmdMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.SistemasDistruidos.message.ComandRequest,
      io.grpc.SistemasDistruidos.message.ComandResponse> getCmdMethod() {
    return getCmdMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.SistemasDistruidos.message.ComandRequest,
      io.grpc.SistemasDistruidos.message.ComandResponse> getCmdMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.SistemasDistruidos.message.ComandRequest, io.grpc.SistemasDistruidos.message.ComandResponse> getCmdMethod;
    if ((getCmdMethod = ComandServiceGrpc.getCmdMethod) == null) {
      synchronized (ComandServiceGrpc.class) {
        if ((getCmdMethod = ComandServiceGrpc.getCmdMethod) == null) {
          ComandServiceGrpc.getCmdMethod = getCmdMethod = 
              io.grpc.MethodDescriptor.<io.grpc.SistemasDistruidos.message.ComandRequest, io.grpc.SistemasDistruidos.message.ComandResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "message.ComandService", "cmd"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.SistemasDistruidos.message.ComandRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.SistemasDistruidos.message.ComandResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ComandServiceMethodDescriptorSupplier("cmd"))
                  .build();
          }
        }
     }
     return getCmdMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ComandServiceStub newStub(io.grpc.Channel channel) {
    return new ComandServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ComandServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ComandServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ComandServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ComandServiceFutureStub(channel);
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static abstract class ComandServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Define a RPC operation Bidirectional streaming RPCs
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.grpc.SistemasDistruidos.message.ComandRequest> cmd(
        io.grpc.stub.StreamObserver<io.grpc.SistemasDistruidos.message.ComandResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getCmdMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCmdMethodHelper(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                io.grpc.SistemasDistruidos.message.ComandRequest,
                io.grpc.SistemasDistruidos.message.ComandResponse>(
                  this, METHODID_CMD)))
          .build();
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class ComandServiceStub extends io.grpc.stub.AbstractStub<ComandServiceStub> {
    private ComandServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ComandServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComandServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ComandServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Define a RPC operation Bidirectional streaming RPCs
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.grpc.SistemasDistruidos.message.ComandRequest> cmd(
        io.grpc.stub.StreamObserver<io.grpc.SistemasDistruidos.message.ComandResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getCmdMethodHelper(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class ComandServiceBlockingStub extends io.grpc.stub.AbstractStub<ComandServiceBlockingStub> {
    private ComandServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ComandServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComandServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ComandServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class ComandServiceFutureStub extends io.grpc.stub.AbstractStub<ComandServiceFutureStub> {
    private ComandServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ComandServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComandServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ComandServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_CMD = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ComandServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ComandServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CMD:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.cmd(
              (io.grpc.stub.StreamObserver<io.grpc.SistemasDistruidos.message.ComandResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ComandServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ComandServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.SistemasDistruidos.message.Message.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ComandService");
    }
  }

  private static final class ComandServiceFileDescriptorSupplier
      extends ComandServiceBaseDescriptorSupplier {
    ComandServiceFileDescriptorSupplier() {}
  }

  private static final class ComandServiceMethodDescriptorSupplier
      extends ComandServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ComandServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ComandServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ComandServiceFileDescriptorSupplier())
              .addMethod(getCmdMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
