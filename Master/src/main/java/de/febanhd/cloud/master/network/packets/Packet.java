package de.febanhd.cloud.master.network.packets;

import de.febanhd.cloud.master.network.PacketListener;
import io.netty.buffer.ByteBuf;

public interface Packet {

    void read(PacketDataSerializer dataSerializer);

    void write(ByteBuf byteBuf);

    void processPacket(PacketListener packetListener);
}
