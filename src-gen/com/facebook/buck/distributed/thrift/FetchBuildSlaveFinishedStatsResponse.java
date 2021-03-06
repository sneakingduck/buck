/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.facebook.buck.distributed.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-11-07")
public class FetchBuildSlaveFinishedStatsResponse implements org.apache.thrift.TBase<FetchBuildSlaveFinishedStatsResponse, FetchBuildSlaveFinishedStatsResponse._Fields>, java.io.Serializable, Cloneable, Comparable<FetchBuildSlaveFinishedStatsResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FetchBuildSlaveFinishedStatsResponse");

  private static final org.apache.thrift.protocol.TField BUILD_SLAVE_FINISHED_STATS_FIELD_DESC = new org.apache.thrift.protocol.TField("buildSlaveFinishedStats", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FetchBuildSlaveFinishedStatsResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FetchBuildSlaveFinishedStatsResponseTupleSchemeFactory());
  }

  public ByteBuffer buildSlaveFinishedStats; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BUILD_SLAVE_FINISHED_STATS((short)1, "buildSlaveFinishedStats");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // BUILD_SLAVE_FINISHED_STATS
          return BUILD_SLAVE_FINISHED_STATS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.BUILD_SLAVE_FINISHED_STATS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BUILD_SLAVE_FINISHED_STATS, new org.apache.thrift.meta_data.FieldMetaData("buildSlaveFinishedStats", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FetchBuildSlaveFinishedStatsResponse.class, metaDataMap);
  }

  public FetchBuildSlaveFinishedStatsResponse() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FetchBuildSlaveFinishedStatsResponse(FetchBuildSlaveFinishedStatsResponse other) {
    if (other.isSetBuildSlaveFinishedStats()) {
      this.buildSlaveFinishedStats = org.apache.thrift.TBaseHelper.copyBinary(other.buildSlaveFinishedStats);
    }
  }

  public FetchBuildSlaveFinishedStatsResponse deepCopy() {
    return new FetchBuildSlaveFinishedStatsResponse(this);
  }

  @Override
  public void clear() {
    this.buildSlaveFinishedStats = null;
  }

  public byte[] getBuildSlaveFinishedStats() {
    setBuildSlaveFinishedStats(org.apache.thrift.TBaseHelper.rightSize(buildSlaveFinishedStats));
    return buildSlaveFinishedStats == null ? null : buildSlaveFinishedStats.array();
  }

  public ByteBuffer bufferForBuildSlaveFinishedStats() {
    return org.apache.thrift.TBaseHelper.copyBinary(buildSlaveFinishedStats);
  }

  public FetchBuildSlaveFinishedStatsResponse setBuildSlaveFinishedStats(byte[] buildSlaveFinishedStats) {
    this.buildSlaveFinishedStats = buildSlaveFinishedStats == null ? (ByteBuffer)null : ByteBuffer.wrap(Arrays.copyOf(buildSlaveFinishedStats, buildSlaveFinishedStats.length));
    return this;
  }

  public FetchBuildSlaveFinishedStatsResponse setBuildSlaveFinishedStats(ByteBuffer buildSlaveFinishedStats) {
    this.buildSlaveFinishedStats = org.apache.thrift.TBaseHelper.copyBinary(buildSlaveFinishedStats);
    return this;
  }

  public void unsetBuildSlaveFinishedStats() {
    this.buildSlaveFinishedStats = null;
  }

  /** Returns true if field buildSlaveFinishedStats is set (has been assigned a value) and false otherwise */
  public boolean isSetBuildSlaveFinishedStats() {
    return this.buildSlaveFinishedStats != null;
  }

  public void setBuildSlaveFinishedStatsIsSet(boolean value) {
    if (!value) {
      this.buildSlaveFinishedStats = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BUILD_SLAVE_FINISHED_STATS:
      if (value == null) {
        unsetBuildSlaveFinishedStats();
      } else {
        setBuildSlaveFinishedStats((ByteBuffer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BUILD_SLAVE_FINISHED_STATS:
      return getBuildSlaveFinishedStats();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BUILD_SLAVE_FINISHED_STATS:
      return isSetBuildSlaveFinishedStats();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FetchBuildSlaveFinishedStatsResponse)
      return this.equals((FetchBuildSlaveFinishedStatsResponse)that);
    return false;
  }

  public boolean equals(FetchBuildSlaveFinishedStatsResponse that) {
    if (that == null)
      return false;

    boolean this_present_buildSlaveFinishedStats = true && this.isSetBuildSlaveFinishedStats();
    boolean that_present_buildSlaveFinishedStats = true && that.isSetBuildSlaveFinishedStats();
    if (this_present_buildSlaveFinishedStats || that_present_buildSlaveFinishedStats) {
      if (!(this_present_buildSlaveFinishedStats && that_present_buildSlaveFinishedStats))
        return false;
      if (!this.buildSlaveFinishedStats.equals(that.buildSlaveFinishedStats))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_buildSlaveFinishedStats = true && (isSetBuildSlaveFinishedStats());
    list.add(present_buildSlaveFinishedStats);
    if (present_buildSlaveFinishedStats)
      list.add(buildSlaveFinishedStats);

    return list.hashCode();
  }

  @Override
  public int compareTo(FetchBuildSlaveFinishedStatsResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetBuildSlaveFinishedStats()).compareTo(other.isSetBuildSlaveFinishedStats());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBuildSlaveFinishedStats()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.buildSlaveFinishedStats, other.buildSlaveFinishedStats);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FetchBuildSlaveFinishedStatsResponse(");
    boolean first = true;

    if (isSetBuildSlaveFinishedStats()) {
      sb.append("buildSlaveFinishedStats:");
      if (this.buildSlaveFinishedStats == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.buildSlaveFinishedStats, sb);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FetchBuildSlaveFinishedStatsResponseStandardSchemeFactory implements SchemeFactory {
    public FetchBuildSlaveFinishedStatsResponseStandardScheme getScheme() {
      return new FetchBuildSlaveFinishedStatsResponseStandardScheme();
    }
  }

  private static class FetchBuildSlaveFinishedStatsResponseStandardScheme extends StandardScheme<FetchBuildSlaveFinishedStatsResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FetchBuildSlaveFinishedStatsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BUILD_SLAVE_FINISHED_STATS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.buildSlaveFinishedStats = iprot.readBinary();
              struct.setBuildSlaveFinishedStatsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, FetchBuildSlaveFinishedStatsResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.buildSlaveFinishedStats != null) {
        if (struct.isSetBuildSlaveFinishedStats()) {
          oprot.writeFieldBegin(BUILD_SLAVE_FINISHED_STATS_FIELD_DESC);
          oprot.writeBinary(struct.buildSlaveFinishedStats);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FetchBuildSlaveFinishedStatsResponseTupleSchemeFactory implements SchemeFactory {
    public FetchBuildSlaveFinishedStatsResponseTupleScheme getScheme() {
      return new FetchBuildSlaveFinishedStatsResponseTupleScheme();
    }
  }

  private static class FetchBuildSlaveFinishedStatsResponseTupleScheme extends TupleScheme<FetchBuildSlaveFinishedStatsResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FetchBuildSlaveFinishedStatsResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBuildSlaveFinishedStats()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetBuildSlaveFinishedStats()) {
        oprot.writeBinary(struct.buildSlaveFinishedStats);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FetchBuildSlaveFinishedStatsResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.buildSlaveFinishedStats = iprot.readBinary();
        struct.setBuildSlaveFinishedStatsIsSet(true);
      }
    }
  }

}

