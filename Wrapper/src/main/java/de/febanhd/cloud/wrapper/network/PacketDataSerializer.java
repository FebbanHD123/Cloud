package de.febanhd.cloud.wrapper.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.util.ByteProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public class PacketDataSerializer extends ByteBuf {

    private final ByteBuf byteBuf;

    public PacketDataSerializer(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public int capacity() {
        return this.byteBuf.capacity();
    }

    public ByteBuf capacity(int i) {
        return this.byteBuf.capacity(i);
    }

    public int maxCapacity() {
        return this.byteBuf.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.byteBuf.alloc();
    }

    public ByteOrder order() {
        return this.byteBuf.order();
    }

    public ByteBuf order(ByteOrder byteorder) {
        return this.byteBuf.order(byteorder);
    }

    public ByteBuf unwrap() {
        return this.byteBuf.unwrap();
    }

    public boolean isDirect() {
        return this.byteBuf.isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return this.byteBuf.isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly() {
        return this.byteBuf.asReadOnly();
    }

    public int readerIndex() {
        return this.byteBuf.readerIndex();
    }

    public ByteBuf readerIndex(int i) {
        return this.byteBuf.readerIndex(i);
    }

    public int writerIndex() {
        return this.byteBuf.writerIndex();
    }

    public ByteBuf writerIndex(int i) {
        return this.byteBuf.writerIndex(i);
    }

    public ByteBuf setIndex(int i, int j) {
        return this.byteBuf.setIndex(i, j);
    }

    public int readableBytes() {
        return this.byteBuf.readableBytes();
    }

    public int writableBytes() {
        return this.byteBuf.writableBytes();
    }

    public int maxWritableBytes() {
        return this.byteBuf.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.byteBuf.isReadable();
    }

    public boolean isReadable(int i) {
        return this.byteBuf.isReadable(i);
    }

    public boolean isWritable() {
        return this.byteBuf.isWritable();
    }

    public boolean isWritable(int i) {
        return this.byteBuf.isWritable(i);
    }

    public ByteBuf clear() {
        return this.byteBuf.clear();
    }

    public ByteBuf markReaderIndex() {
        return this.byteBuf.markReaderIndex();
    }

    public ByteBuf resetReaderIndex() {
        return this.byteBuf.resetReaderIndex();
    }

    public ByteBuf markWriterIndex() {
        return this.byteBuf.markWriterIndex();
    }

    public ByteBuf resetWriterIndex() {
        return this.byteBuf.resetWriterIndex();
    }

    public ByteBuf discardReadBytes() {
        return this.byteBuf.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes() {
        return this.byteBuf.discardSomeReadBytes();
    }

    public ByteBuf ensureWritable(int i) {
        return this.byteBuf.ensureWritable(i);
    }

    public int ensureWritable(int i, boolean flag) {
        return this.byteBuf.ensureWritable(i, flag);
    }

    public boolean getBoolean(int i) {
        return this.byteBuf.getBoolean(i);
    }

    public byte getByte(int i) {
        return this.byteBuf.getByte(i);
    }

    public short getUnsignedByte(int i) {
        return this.byteBuf.getUnsignedByte(i);
    }

    public short getShort(int i) {
        return this.byteBuf.getShort(i);
    }

    @Override
    public short getShortLE(int i) {
        return this.byteBuf.getShort(i);
    }

    public int getUnsignedShort(int i) {
        return this.byteBuf.getUnsignedShort(i);
    }

    @Override
    public int getUnsignedShortLE(int i) {
        return this.byteBuf.getUnsignedShortLE(i);
    }

    public int getMedium(int i) {
        return this.byteBuf.getMedium(i);
    }

    @Override
    public int getMediumLE(int i) {
        return this.byteBuf.getMediumLE(i);
    }

    public int getUnsignedMedium(int i) {
        return this.byteBuf.getUnsignedMedium(i);
    }

    @Override
    public int getUnsignedMediumLE(int i) {
        return this.byteBuf.getUnsignedMediumLE(i);
    }

    public int getInt(int i) {
        return this.byteBuf.getInt(i);
    }

    @Override
    public int getIntLE(int i) {
        return this.byteBuf.getIntLE(i);
    }

    public long getUnsignedInt(int i) {
        return this.byteBuf.getUnsignedInt(i);
    }

    @Override
    public long getUnsignedIntLE(int i) {
        return this.byteBuf.getUnsignedIntLE(i);
    }

    public long getLong(int i) {
        return this.byteBuf.getLong(i);
    }

    @Override
    public long getLongLE(int i) {
        return this.byteBuf.getLongLE(i);
    }

    public char getChar(int i) {
        return this.byteBuf.getChar(i);
    }

    public float getFloat(int i) {
        return this.byteBuf.getFloat(i);
    }

    public double getDouble(int i) {
        return this.byteBuf.getDouble(i);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf) {
        return this.byteBuf.getBytes(i, bytebuf);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
        return this.byteBuf.getBytes(i, bytebuf, j);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.byteBuf.getBytes(i, bytebuf, j, k);
    }

    public ByteBuf getBytes(int i, byte[] abyte) {
        return this.byteBuf.getBytes(i, abyte);
    }

    public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
        return this.byteBuf.getBytes(i, abyte, j, k);
    }

    public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
        return this.byteBuf.getBytes(i, bytebuffer);
    }

    public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
        return this.byteBuf.getBytes(i, outputstream, j);
    }

    public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
        return this.byteBuf.getBytes(i, gatheringbytechannel, j);
    }

    @Override
    public int getBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return this.getBytes(i, fileChannel, l, i1);
    }

    @Override
    public CharSequence getCharSequence(int i, int i1, Charset charset) {
        return this.byteBuf.getCharSequence(i, i1, charset);
    }

    public ByteBuf setBoolean(int i, boolean flag) {
        return this.byteBuf.setBoolean(i, flag);
    }

    public ByteBuf setByte(int i, int j) {
        return this.byteBuf.setByte(i, j);
    }

    public ByteBuf setShort(int i, int j) {
        return this.byteBuf.setShort(i, j);
    }

    @Override
    public ByteBuf setShortLE(int i, int i1) {
        return this.byteBuf.setShortLE(i, i1);
    }

    public ByteBuf setMedium(int i, int j) {
        return this.byteBuf.setMedium(i, j);
    }

    @Override
    public ByteBuf setMediumLE(int i, int i1) {
        return this.byteBuf.setMediumLE(i, i1);
    }

    public ByteBuf setInt(int i, int j) {
        return this.byteBuf.setInt(i, j);
    }

    @Override
    public ByteBuf setIntLE(int i, int i1) {
        return this.byteBuf.setIntLE(i, i1);
    }

    public ByteBuf setLong(int i, long j) {
        return this.byteBuf.setLong(i, j);
    }

    @Override
    public ByteBuf setLongLE(int i, long l) {
        return this.byteBuf.setLongLE(i, l);
    }

    public ByteBuf setChar(int i, int j) {
        return this.byteBuf.setChar(i, j);
    }

    public ByteBuf setFloat(int i, float f) {
        return this.byteBuf.setFloat(i, f);
    }

    public ByteBuf setDouble(int i, double d0) {
        return this.byteBuf.setDouble(i, d0);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf) {
        return this.byteBuf.setBytes(i, bytebuf);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
        return this.byteBuf.setBytes(i, bytebuf, j);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.byteBuf.setBytes(i, bytebuf, j, k);
    }

    public ByteBuf setBytes(int i, byte[] abyte) {
        return this.byteBuf.setBytes(i, abyte);
    }

    public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
        return this.byteBuf.setBytes(i, abyte, j, k);
    }

    public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
        return this.byteBuf.setBytes(i, bytebuffer);
    }

    public int setBytes(int i, InputStream inputstream, int j) throws IOException {
        return this.byteBuf.setBytes(i, inputstream, j);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
        return this.byteBuf.setBytes(i, scatteringbytechannel, j);
    }

    @Override
    public int setBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return this.byteBuf.setBytes(i, fileChannel, l, i1);
    }

    public ByteBuf setZero(int i, int j) {
        return this.byteBuf.setZero(i, j);
    }

    @Override
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return this.byteBuf.setCharSequence(i, charSequence, charset);
    }

    public boolean readBoolean() {
        return this.byteBuf.readBoolean();
    }

    public byte readByte() {
        return this.byteBuf.readByte();
    }

    public short readUnsignedByte() {
        return this.byteBuf.readUnsignedByte();
    }

    public short readShort() {
        return this.byteBuf.readShort();
    }

    @Override
    public short readShortLE() {
        return this.byteBuf.readShortLE();
    }

    public int readUnsignedShort() {
        return this.byteBuf.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return this.byteBuf.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.byteBuf.readMedium();
    }

    @Override
    public int readMediumLE() {
        return this.byteBuf.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.byteBuf.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return this.byteBuf.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.byteBuf.readInt();
    }

    @Override
    public int readIntLE() {
        return this.byteBuf.readIntLE();
    }

    public long readUnsignedInt() {
        return this.byteBuf.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return this.byteBuf.readUnsignedIntLE();
    }

    public long readLong() {
        return this.byteBuf.readLong();
    }

    @Override
    public long readLongLE() {
        return this.byteBuf.readIntLE();
    }

    public char readChar() {
        return this.byteBuf.readChar();
    }

    public float readFloat() {
        return this.byteBuf.readFloat();
    }

    public double readDouble() {
        return this.byteBuf.readDouble();
    }

    public ByteBuf readBytes(int i) {
        return this.byteBuf.readBytes(i);
    }

    public ByteBuf readSlice(int i) {
        return this.byteBuf.readSlice(i);
    }

    @Override
    public ByteBuf readRetainedSlice(int i) {
        return null;
    }

    public ByteBuf readBytes(ByteBuf bytebuf) {
        return this.byteBuf.readBytes(bytebuf);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i) {
        return this.byteBuf.readBytes(bytebuf, i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
        return this.byteBuf.readBytes(bytebuf, i, j);
    }

    public ByteBuf readBytes(byte[] abyte) {
        return this.byteBuf.readBytes(abyte);
    }

    public ByteBuf readBytes(byte[] abyte, int i, int j) {
        return this.byteBuf.readBytes(abyte, i, j);
    }

    public ByteBuf readBytes(ByteBuffer bytebuffer) {
        return this.byteBuf.readBytes(bytebuffer);
    }

    public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
        return this.byteBuf.readBytes(outputstream, i);
    }

    public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
        return this.byteBuf.readBytes(gatheringbytechannel, i);
    }

    @Override
    public CharSequence readCharSequence(int i, Charset charset) {
        return null;
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return 0;
    }

    public ByteBuf skipBytes(int i) {
        return this.byteBuf.skipBytes(i);
    }

    public ByteBuf writeBoolean(boolean flag) {
        return this.byteBuf.writeBoolean(flag);
    }

    public ByteBuf writeByte(int i) {
        return this.byteBuf.writeByte(i);
    }

    public ByteBuf writeShort(int i) {
        return this.byteBuf.writeShort(i);
    }

    @Override
    public ByteBuf writeShortLE(int i) {
        return null;
    }

    public ByteBuf writeMedium(int i) {
        return this.byteBuf.writeMedium(i);
    }

    @Override
    public ByteBuf writeMediumLE(int i) {
        return null;
    }

    public ByteBuf writeInt(int i) {
        return this.byteBuf.writeInt(i);
    }

    @Override
    public ByteBuf writeIntLE(int i) {
        return null;
    }

    public ByteBuf writeLong(long i) {
        return this.byteBuf.writeLong(i);
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        return null;
    }

    public ByteBuf writeChar(int i) {
        return this.byteBuf.writeChar(i);
    }

    public ByteBuf writeFloat(float f) {
        return this.byteBuf.writeFloat(f);
    }

    public ByteBuf writeDouble(double d0) {
        return this.byteBuf.writeDouble(d0);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf) {
        return this.byteBuf.writeBytes(bytebuf);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
        return this.byteBuf.writeBytes(bytebuf, i);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
        return this.byteBuf.writeBytes(bytebuf, i, j);
    }

    public ByteBuf writeBytes(byte[] abyte) {
        return this.byteBuf.writeBytes(abyte);
    }

    public ByteBuf writeBytes(byte[] abyte, int i, int j) {
        return this.byteBuf.writeBytes(abyte, i, j);
    }

    public ByteBuf writeBytes(ByteBuffer bytebuffer) {
        return this.byteBuf.writeBytes(bytebuffer);
    }

    public int writeBytes(InputStream inputstream, int i) throws IOException {
        return this.byteBuf.writeBytes(inputstream, i);
    }

    public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
        return this.byteBuf.writeBytes(scatteringbytechannel, i);
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return 0;
    }

    public ByteBuf writeZero(int i) {
        return this.byteBuf.writeZero(i);
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return 0;
    }

    public int indexOf(int i, int j, byte b0) {
        return this.byteBuf.indexOf(i, j, b0);
    }

    public int bytesBefore(byte b0) {
        return this.byteBuf.bytesBefore(b0);
    }

    public int bytesBefore(int i, byte b0) {
        return this.byteBuf.bytesBefore(i, b0);
    }

    public int bytesBefore(int i, int j, byte b0) {
        return this.byteBuf.bytesBefore(i, j, b0);
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        return 0;
    }

    @Override
    public int forEachByte(int i, int i1, ByteProcessor byteProcessor) {
        return 0;
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return 0;
    }

    @Override
    public int forEachByteDesc(int i, int i1, ByteProcessor byteProcessor) {
        return 0;
    }

    public int forEachByte(ByteBufProcessor bytebufprocessor) {
        return this.byteBuf.forEachByte(bytebufprocessor);
    }

    public int forEachByte(int i, int j, ByteBufProcessor bytebufprocessor) {
        return this.byteBuf.forEachByte(i, j, bytebufprocessor);
    }

    public int forEachByteDesc(ByteBufProcessor bytebufprocessor) {
        return this.byteBuf.forEachByteDesc(bytebufprocessor);
    }

    public int forEachByteDesc(int i, int j, ByteBufProcessor bytebufprocessor) {
        return this.byteBuf.forEachByteDesc(i, j, bytebufprocessor);
    }

    public ByteBuf copy() {
        return this.byteBuf.copy();
    }

    public ByteBuf copy(int i, int j) {
        return this.byteBuf.copy(i, j);
    }

    public ByteBuf slice() {
        return this.byteBuf.slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return null;
    }

    public ByteBuf slice(int i, int j) {
        return this.byteBuf.slice(i, j);
    }

    @Override
    public ByteBuf retainedSlice(int i, int i1) {
        return null;
    }

    public ByteBuf duplicate() {
        return this.byteBuf.duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return null;
    }

    public int nioBufferCount() {
        return this.byteBuf.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.byteBuf.nioBuffer();
    }

    public ByteBuffer nioBuffer(int i, int j) {
        return this.byteBuf.nioBuffer(i, j);
    }

    public ByteBuffer internalNioBuffer(int i, int j) {
        return this.byteBuf.internalNioBuffer(i, j);
    }

    public ByteBuffer[] nioBuffers() {
        return this.byteBuf.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int i, int j) {
        return this.byteBuf.nioBuffers(i, j);
    }

    public boolean hasArray() {
        return this.byteBuf.hasArray();
    }

    public byte[] array() {
        return this.byteBuf.array();
    }

    public int arrayOffset() {
        return this.byteBuf.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.byteBuf.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.byteBuf.memoryAddress();
    }

    public String toString(Charset charset) {
        return this.byteBuf.toString(charset);
    }

    public String toString(int i, int j, Charset charset) {
        return this.byteBuf.toString(i, j, charset);
    }

    public int hashCode() {
        return this.byteBuf.hashCode();
    }

    public boolean equals(Object object) {
        return this.byteBuf.equals(object);
    }

    public int compareTo(ByteBuf bytebuf) {
        return this.byteBuf.compareTo(bytebuf);
    }

    public String toString() {
        return this.byteBuf.toString();
    }

    public ByteBuf retain(int i) {
        return this.byteBuf.retain(i);
    }

    public ByteBuf retain() {
        return this.byteBuf.retain();
    }

    @Override
    public ByteBuf touch() {
        return null;
    }

    @Override
    public ByteBuf touch(Object o) {
        return null;
    }

    public int refCnt() {
        return this.byteBuf.refCnt();
    }

    public boolean release() {
        return this.byteBuf.release();
    }

    public boolean release(int i) {
        return this.byteBuf.release(i);
    }
}
