package de.febanhd.cloud.wrapper.network.masterclient.packets.server;

import de.febanhd.cloud.server.ServerInformation;
import de.febanhd.cloud.utils.ByteBufUtils;
import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.network.PacketListener;
import de.febanhd.cloud.wrapper.network.Packet;
import de.febanhd.cloud.wrapper.network.PacketDataSerializer;
import de.febanhd.cloud.wrapper.servers.AbstractServer;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class PacketStartServer implements Packet {

    private ServerInformation serverInformation;

    public PacketStartServer(AbstractServer<?> server) {
        this.serverInformation = new ServerInformation(server.getServerName(), "", server.getServerPort(), CloudWrapper.getInstance().getWrapperID(), server.getServerGroup().getServerType());
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
