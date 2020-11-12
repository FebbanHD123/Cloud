package de.febanhd.cloud.wrapper.network.handler;

import de.febanhd.cloud.wrapper.network.masterclient.MasterClientPacketRegistry;
import de.febanhd.cloud.wrapper.network.Packet;
import de.febanhd.cloud.wrapper.network.PacketDataSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int id = byteBuf.readInt();
        Packet packet = MasterClientPacketRegistry.byId(id);

        PacketDataSerializer packetDataSerializer = new PacketDataSerializer(byteBuf);

        packet.read(packetDataSerializer);

        list.add(packet);
    }
}
