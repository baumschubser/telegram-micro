package mtproto;
import bouncycastle.BigInteger;

import support.ByteArrayPlus;
import support.Integer128;
import support.Integer256;
import support.RandomPlus;
import support.ArrayPlus;
import support.Encode;

public class Serializer {
  ByteArrayPlus data;
  
  public Serializer() {
    data = new ByteArrayPlus();
  }
  
  public Serializer append_vector_long(long[] values) {
    append_int(CombinatorIds.vector);
    append_int(values.length);
    for (int i = 0; i < values.length; i += 1) {
      append_long(values[i]);
    }
    return this;
  }
  
  public Serializer append_vector_byte_strings(byte[][] data) {
    append_int(CombinatorIds.vector);
    append_int(data.length);
    for (int i = 0; i < data.length; i += 1) {
      append_byte_string(data[i]);
    }
    return this;
  }
  
  public Serializer append_string(String data) {
    return append_byte_string(data.getBytes());
  }
  
  public Serializer append_byte_string(byte[] data) {
    Serializer to_append = new Serializer();
    if (data.length <= 253) {
      to_append.append_byte((byte)data.length);
    } else {
      to_append.append_byte((byte)254);
      to_append.append_int24(data.length);
    }
    
    return append_raw_bytes(
      to_append.append_raw_bytes(data)
        .pad_to_alignment(4)
        .end()
    );
  }
  
  public Serializer append_long_as_byte_string(long to_write) {
    return append_byte_string(ArrayPlus.remove_leading_zeroes(Encode.Big.long_encode(to_write)));
  }
  
  public Serializer append_BigInteger(BigInteger to_write) {
    return append_byte_string(to_write.magnitudeToBytes());
  }
  
  public Serializer append_byte(byte to_write) {
    data.append_byte(to_write);
    return this;
  }

  public Serializer append_long(long to_write) {
    data.append_long(to_write);
    return this;
  }

  public Serializer append_int(int to_write) {
    data.append_int(to_write);
    return this;
  }

  public Serializer append_int24(int to_write) {
    data.append_int24(to_write);
    return this;
  }

  public Serializer append_Integer128(Integer128 to_write) {
    data.append_Integer128(to_write);
    return this;
  }

  public Serializer append_Integer256(Integer256 to_write) {
    data.append_Integer256(to_write);
    return this;
  }

  public Serializer append_raw_bytes(byte[] to_write) {
    data.append_raw_bytes(to_write);
    return this;
  }

  public Serializer append_raw_bytes_up_to(byte[] to_write, int length) {
    data.append_raw_bytes_up_to(to_write, length);
    return this;
  }
  
  public Serializer append_raw_bytes_from_up_to(byte[] to_write, int from, int length) {
    data.append_raw_bytes_from_up_to(to_write, from, length);
    return this;
  }
  
  public Serializer pad_to_length(int length, RandomPlus random_number_generator) {
    data.pad_to_length(length, random_number_generator);
    return this;
  }
  
  public Serializer pad_to_alignment(int alignment) {
    data.pad_to_alignment(alignment);
    return this;
  }
  
  public Serializer pad_to_alignment(int alignment, RandomPlus random_number_generator) {
    data.pad_to_alignment(alignment, random_number_generator);
    return this;
  }
  
  public byte[] end() {
    return data.toByteArray();
  }
}
