package de.febanhd.cloud.master.network.packets.server;

import de.febanhd.cloud.master.network.packets.Packet;
import de.febanhd.cloud.master.network.packets.PacketDataSerializer;
import de.febanhd.cloud.master.network.PacketListener;
import de.febanhd.cloud.server.ServerInformation;
import de.febanhd.cloud.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class PacketStartServer implements Packet {

    private ServerInformation serverInformation;

    public PacketStartServer(ServerInformation serverInformation) {
        this.serverInformation = serverInformation;
    }

    public PacketStartServer() {}

    @Override
    public void read(PacketDataSerializer dataSerializer) {
        this.serverInformation = ServerInformation.fromString(ByteBufUtils.readString(dataSerializer));
    }

    @Override
    public void write(ByteBuf byteBuf) {
        ByteBufUtils.writeString(byteBuf, serverInformation.toString());
    }

    @Override
    public void processPacket(PacketListener packetListener) {
        packetListener.handleStartServer(this);
    }
}
