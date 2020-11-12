package de.febanhd.cloud.wrapper.network;

import io.netty.buffer.ByteBuf;

public interface Packet {

    void read(PacketDataSerializer dataSerializer);

    void write(ByteBuf byteBuf);

    void processPacket(PacketListener packetListener);
}
