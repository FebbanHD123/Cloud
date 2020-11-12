package de.febanhd.cloud.master.network.keepalive;

import de.febanhd.cloud.master.network.packets.Packet;
import de.febanhd.cloud.master.network.packets.PacketDataSerializer;
import de.febanhd.cloud.master.network.PacketListener;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.util.Random;

@Getter
public class PacketKeepAlive implements Packet {

    private long time;
    private int id;

    public PacketKeepAlive(long time) {
        this.time = time;
        this.id = this.getRandomID();
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

    private int getRandomID() {
        return new Random().nextInt(10000);
    }
}
