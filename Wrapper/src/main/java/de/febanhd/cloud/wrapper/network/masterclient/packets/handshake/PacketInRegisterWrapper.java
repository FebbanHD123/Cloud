package de.febanhd.cloud.wrapper.network.masterclient.packets.handshake;

import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.network.Packet;
import de.febanhd.cloud.wrapper.network.PacketDataSerializer;
import de.febanhd.cloud.wrapper.network.PacketListener;
import io.netty.buffer.ByteBuf;

public class PacketInRegisterWrapper implements Packet {

    private int wrapperID;

    @Override
    public void read(PacketDataSerializer dataSerializer) {
        this.wrapperID = dataSerializer.readInt();
    }

    @Override
    public void write(ByteBuf byteBuf) {

    }

    @Override
    public void processPacket(PacketListener packetListener) {
        CloudWrapper.getInstance().registerWrapper(this.wrapperID);
    }
}
