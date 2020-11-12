package de.febanhd.cloud.wrapper.network.handler;

import de.febanhd.cloud.wrapper.network.masterclient.MasterClientPacketRegistry;
import de.febanhd.cloud.wrapper.network.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf byteBuf) throws Exception {
        int id = MasterClientPacketRegistry.getId(packet);
        if(id == -1)
            throw new NullPointerException("Couldn't find packet id of packet " + packet.getClass().getSimpleName());

        byteBuf.writeInt(id);
        packet.write(byteBuf);
    }
}
