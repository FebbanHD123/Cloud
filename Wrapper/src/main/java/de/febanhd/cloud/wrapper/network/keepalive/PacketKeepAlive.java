package de.febanhd.cloud.wrapper.network.keepalive;

import de.febanhd.cloud.wrapper.network.Packet;
import de.febanhd.cloud.wrapper.network.PacketDataSerializer;
import de.febanhd.cloud.wrapper.network.PacketListener;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class PacketKeepAlive implements Packet {

    private long time;
    private int id;

    public PacketKeepAlive(long time, int id) {
        this.time = time;
        this.id = id;
    }

    public PacketKeepAlive() {

    }


    @Override
    public void read(PacketDataSerializer dataSerializer) {
        this.id = dataSerializer.readInt();
        this.time = dataSerializer.readLong();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeInt(this.id);
        byteBuf.writeLong(this.time);
    }

    @Override
    public void processPacket(PacketListener packetListener) {
        packetListener.handleKeepAlive(this);
    }
}
