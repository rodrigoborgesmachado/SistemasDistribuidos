// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package io.grpc.SistemasDistruidos.message;

public final class Message {
  private Message() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_message_ComandRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_message_ComandRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_message_ComandResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_message_ComandResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rmessage.proto\022\007message\"\035\n\rComandReques" +
      "t\022\014\n\004comm\030\001 \001(\t\"\035\n\016ComandResponse\022\013\n\003cmd" +
      "\030\001 \001(\t2K\n\rComandService\022:\n\003cmd\022\026.message" +
      ".ComandRequest\032\027.message.ComandResponse(" +
      "\0010\001B5\n\"io.grpc.SistemasDistruidos.messag" +
      "eB\007MessageP\001\242\002\003MSGb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_message_ComandRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_message_ComandRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_message_ComandRequest_descriptor,
        new java.lang.String[] { "Comm", });
    internal_static_message_ComandResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_message_ComandResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_message_ComandResponse_descriptor,
        new java.lang.String[] { "Cmd", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
