package de.febanhd.cloud.master.network.packets.handshake;

import de.febanhd.cloud.utils.ByteBufUtils;
import de.febanhd.cloud.master.network.packets.Packet;
import de.febanhd.cloud.master.network.packets.PacketDataSerializer;
import de.febanhd.cloud.master.network.PacketListener;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class PacketInRegisterWrapper implements Packet {

    private String masterID;
    private int wrapperID;

    @Override
    public void read(PacketDataSerializer dataSerializer) {
        this.wrapperID = dataSerializer.readInt();
        this.masterID = ByteBufUtils.readString(dataSerializer);
    }

    @Override
    public void write(ByteBuf byteBuf) {

    }

    @Override
    public void processPacket(PacketListener packetListener) {
        packetListener.handleRegisterWrapper(this);
    }
}
