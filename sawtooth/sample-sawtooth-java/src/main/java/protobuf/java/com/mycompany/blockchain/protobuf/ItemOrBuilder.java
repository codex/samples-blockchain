// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Item.proto

package protobuf.java.com.mycompany.blockchain.protobuf;

public interface ItemOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Item)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *entity id
   * </pre>
   *
   * <code>int32 id = 1;</code>
   */
  int getId();

  /**
   * <pre>
   *the name 
   * </pre>
   *
   * <code>string name = 2;</code>
   */
  java.lang.String getName();
  /**
   * <pre>
   *the name 
   * </pre>
   *
   * <code>string name = 2;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   *the color
   * </pre>
   *
   * <code>string color = 3;</code>
   */
  java.lang.String getColor();
  /**
   * <pre>
   *the color
   * </pre>
   *
   * <code>string color = 3;</code>
   */
  com.google.protobuf.ByteString
      getColorBytes();

  /**
   * <pre>
   *the price
   * </pre>
   *
   * <code>float price = 4;</code>
   */
  float getPrice();
}