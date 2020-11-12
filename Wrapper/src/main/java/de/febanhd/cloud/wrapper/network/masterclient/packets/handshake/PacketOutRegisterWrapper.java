package de.febanhd.cloud.wrapper.network.masterclient.packets.handshake;

import de.febanhd.cloud.utils.ByteBufUtils;
import de.febanhd.cloud.wrapper.network.Packet;
import de.febanhd.cloud.wrapper.network.PacketDataSerializer;
import de.febanhd.cloud.wrapper.network.PacketListener;
import io.netty.buffer.ByteBuf;

public class PacketOutRegisterWrapper implements Packet {

    private String masterID;
    private int wrapperID;

    public PacketOutRegisterWrapper(String masterID, int wrapperID) {
        this.masterID = masterID;
        this.wrapperID = wrapperID;
    }

    public PacketOutRegisterWrapper() {}

    @Override
    public void read(PacketDataSerializer dataSerializer) {

    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeInt(this.wrapperID);
        ByteBufUtils.writeString(byteBuf, this.masterID);
    }

    @Override
    public void processPacket(PacketListener packetListener) {

    }
}
