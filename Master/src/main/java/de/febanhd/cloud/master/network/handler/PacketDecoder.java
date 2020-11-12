package de.febanhd.cloud.master.network.handler;

import de.febanhd.cloud.master.network.PacketRegistry;
import de.febanhd.cloud.master.network.packets.Packet;
import de.febanhd.cloud.master.network.packets.PacketDataSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int id = byteBuf.readInt();
        Packet packet = PacketRegistry.byId(id);

        if(packet == null) return;

        PacketDataSerializer packetDataSerializer = new PacketDataSerializer(byteBuf);

        packet.read(packetDataSerializer);

        list.add(packet);
    }
}
