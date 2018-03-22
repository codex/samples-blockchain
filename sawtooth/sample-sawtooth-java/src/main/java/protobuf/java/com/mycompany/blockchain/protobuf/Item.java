// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Item.proto

package protobuf.java.com.mycompany.blockchain.protobuf;

/**
 * <pre>
 *of a structured payload type "create,id=00001,itemName=Computer,colour=red,price=1000";
 * </pre>
 *
 * Protobuf type {@code Item}
 */
public  final class Item extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:Item)
    ItemOrBuilder {
  // Use Item.newBuilder() to construct.
  private Item(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Item() {
    id_ = 0;
    name_ = "";
    color_ = "";
    price_ = 0F;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private Item(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            id_ = input.readInt32();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            color_ = s;
            break;
          }
          case 37: {

            price_ = input.readFloat();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return protobuf.java.com.mycompany.blockchain.protobuf.ItemOuterClass.internal_static_Item_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return protobuf.java.com.mycompany.blockchain.protobuf.ItemOuterClass.internal_static_Item_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            protobuf.java.com.mycompany.blockchain.protobuf.Item.class, protobuf.java.com.mycompany.blockchain.protobuf.Item.Builder.class);
  }

  public static final int ID_FIELD_NUMBER = 1;
  private int id_;
  /**
   * <pre>
   *entity id
   * </pre>
   *
   * <code>int32 id = 1;</code>
   */
  public int getId() {
    return id_;
  }

  public static final int NAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object name_;
  /**
   * <pre>
   *the name 
   * </pre>
   *
   * <code>string name = 2;</code>
   */
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *the name 
   * </pre>
   *
   * <code>string name = 2;</code>
   */
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int COLOR_FIELD_NUMBER = 3;
  private volatile java.lang.Object color_;
  /**
   * <pre>
   *the color
   * </pre>
   *
   * <code>string color = 3;</code>
   */
  public java.lang.String getColor() {
    java.lang.Object ref = color_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      color_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *the color
   * </pre>
   *
   * <code>string color = 3;</code>
   */
  public com.google.protobuf.ByteString
      getColorBytes() {
    java.lang.Object ref = color_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      color_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PRICE_FIELD_NUMBER = 4;
  private float price_;
  /**
   * <pre>
   *the price
   * </pre>
   *
   * <code>float price = 4;</code>
   */
  public float getPrice() {
    return price_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (id_ != 0) {
      output.writeInt32(1, id_);
    }
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
    }
    if (!getColorBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, color_);
    }
    if (price_ != 0F) {
      output.writeFloat(4, price_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (id_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, id_);
    }
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, name_);
    }
    if (!getColorBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, color_);
    }
    if (price_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(4, price_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof protobuf.java.com.mycompany.blockchain.protobuf.Item)) {
      return super.equals(obj);
    }
    protobuf.java.com.mycompany.blockchain.protobuf.Item other = (protobuf.java.com.mycompany.blockchain.protobuf.Item) obj;

    boolean result = true;
    result = result && (getId()
        == other.getId());
    result = result && getName()
        .equals(other.getName());
    result = result && getColor()
        .equals(other.getColor());
    result = result && (
        java.lang.Float.floatToIntBits(getPrice())
        == java.lang.Float.floatToIntBits(
            other.getPrice()));
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + getId();
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    hash = (37 * hash) + COLOR_FIELD_NUMBER;
    hash = (53 * hash) + getColor().hashCode();
    hash = (37 * hash) + PRICE_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getPrice());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static protobuf.java.com.mycompany.blockchain.protobuf.Item parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(protobuf.java.com.mycompany.blockchain.protobuf.Item prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   *of a structured payload type "create,id=00001,itemName=Computer,colour=red,price=1000";
   * </pre>
   *
   * Protobuf type {@code Item}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:Item)
      protobuf.java.com.mycompany.blockchain.protobuf.ItemOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return protobuf.java.com.mycompany.blockchain.protobuf.ItemOuterClass.internal_static_Item_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return protobuf.java.com.mycompany.blockchain.protobuf.ItemOuterClass.internal_static_Item_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              protobuf.java.com.mycompany.blockchain.protobuf.Item.class, protobuf.java.com.mycompany.blockchain.protobuf.Item.Builder.class);
    }

    // Construct using protobuf.java.com.mycompany.blockchain.protobuf.Item.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      id_ = 0;

      name_ = "";

      color_ = "";

      price_ = 0F;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return protobuf.java.com.mycompany.blockchain.protobuf.ItemOuterClass.internal_static_Item_descriptor;
    }

    public protobuf.java.com.mycompany.blockchain.protobuf.Item getDefaultInstanceForType() {
      return protobuf.java.com.mycompany.blockchain.protobuf.Item.getDefaultInstance();
    }

    public protobuf.java.com.mycompany.blockchain.protobuf.Item build() {
      protobuf.java.com.mycompany.blockchain.protobuf.Item result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public protobuf.java.com.mycompany.blockchain.protobuf.Item buildPartial() {
      protobuf.java.com.mycompany.blockchain.protobuf.Item result = new protobuf.java.com.mycompany.blockchain.protobuf.Item(this);
      result.id_ = id_;
      result.name_ = name_;
      result.color_ = color_;
      result.price_ = price_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof protobuf.java.com.mycompany.blockchain.protobuf.Item) {
        return mergeFrom((protobuf.java.com.mycompany.blockchain.protobuf.Item)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(protobuf.java.com.mycompany.blockchain.protobuf.Item other) {
      if (other == protobuf.java.com.mycompany.blockchain.protobuf.Item.getDefaultInstance()) return this;
      if (other.getId() != 0) {
        setId(other.getId());
      }
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      if (!other.getColor().isEmpty()) {
        color_ = other.color_;
        onChanged();
      }
      if (other.getPrice() != 0F) {
        setPrice(other.getPrice());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      protobuf.java.com.mycompany.blockchain.protobuf.Item parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (protobuf.java.com.mycompany.blockchain.protobuf.Item) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int id_ ;
    /**
     * <pre>
     *entity id
     * </pre>
     *
     * <code>int32 id = 1;</code>
     */
    public int getId() {
      return id_;
    }
    /**
     * <pre>
     *entity id
     * </pre>
     *
     * <code>int32 id = 1;</code>
     */
    public Builder setId(int value) {
      
      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *entity id
     * </pre>
     *
     * <code>int32 id = 1;</code>
     */
    public Builder clearId() {
      
      id_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object name_ = "";
    /**
     * <pre>
     *the name 
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *the name 
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *the name 
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *the name 
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *the name 
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object color_ = "";
    /**
     * <pre>
     *the color
     * </pre>
     *
     * <code>string color = 3;</code>
     */
    public java.lang.String getColor() {
      java.lang.Object ref = color_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        color_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *the color
     * </pre>
     *
     * <code>string color = 3;</code>
     */
    public com.google.protobuf.ByteString
        getColorBytes() {
      java.lang.Object ref = color_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        color_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *the color
     * </pre>
     *
     * <code>string color = 3;</code>
     */
    public Builder setColor(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      color_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *the color
     * </pre>
     *
     * <code>string color = 3;</code>
     */
    public Builder clearColor() {
      
      color_ = getDefaultInstance().getColor();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *the color
     * </pre>
     *
     * <code>string color = 3;</code>
     */
    public Builder setColorBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      color_ = value;
      onChanged();
      return this;
    }

    private float price_ ;
    /**
     * <pre>
     *the price
     * </pre>
     *
     * <code>float price = 4;</code>
     */
    public float getPrice() {
      return price_;
    }
    /**
     * <pre>
     *the price
     * </pre>
     *
     * <code>float price = 4;</code>
     */
    public Builder setPrice(float value) {
      
      price_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *the price
     * </pre>
     *
     * <code>float price = 4;</code>
     */
    public Builder clearPrice() {
      
      price_ = 0F;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:Item)
  }

  // @@protoc_insertion_point(class_scope:Item)
  private static final protobuf.java.com.mycompany.blockchain.protobuf.Item DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new protobuf.java.com.mycompany.blockchain.protobuf.Item();
  }

  public static protobuf.java.com.mycompany.blockchain.protobuf.Item getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Item>
      PARSER = new com.google.protobuf.AbstractParser<Item>() {
    public Item parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Item(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Item> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Item> getParserForType() {
    return PARSER;
  }

  public protobuf.java.com.mycompany.blockchain.protobuf.Item getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

