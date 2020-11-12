package de.febanhd.cloud.wrapper.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;

@Getter
public class NetworkManager extends SimpleChannelInboundHandler<Packet> {

    private Channel channel;
    private final PacketListener packetListener;

    public NetworkManager() {
        this.packetListener = new PacketListener(this);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        packet.processPacket(this.packetListener);
    }

    public void sendPacket(Packet packet) {
        this.channel.writeAndFlush(packet, this.channel.voidPromise());
    }
}
