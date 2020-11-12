package de.febanhd.cloud.master.network.packets.handshake;

import de.febanhd.cloud.master.network.packets.Packet;
import de.febanhd.cloud.master.network.packets.PacketDataSerializer;
import de.febanhd.cloud.master.network.PacketListener;
import io.netty.buffer.ByteBuf;

public class PacketOutRegisterWrapper implements Packet {

    private int wrapperID;

    public PacketOutRegisterWrapper(int wrapperID) {
        this.wrapperID = wrapperID;
    }

    public PacketOutRegisterWrapper() {}

    @Override
    public void read(PacketDataSerializer dataSerializer) {

    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeInt(this.wrapperID);
    }

    @Override
    public void processPacket(PacketListener packetListener) {

    }
}
