package de.febanhd.cloud.master.wrapper;

import de.febanhd.cloud.master.CloudMaster;
import de.febanhd.cloud.master.network.NetworkManager;
import de.febanhd.cloud.master.network.keepalive.KeepAliveManager;
import de.febanhd.cloud.master.network.packets.Packet;
import de.febanhd.cloud.master.network.packets.handshake.PacketInRegisterWrapper;
import de.febanhd.cloud.master.network.packets.handshake.PacketOutRegisterWrapper;
import de.febanhd.cloud.master.network.packets.server.PacketStartServer;
import de.febanhd.cloud.server.ServerInformation;
import de.febanhd.cloud.server.ServerType;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CloudWrapper extends NetworkManager {

    private static final List<Integer> USES_WRAPPER_IDS = new ArrayList<>();

    private static int getID() {
        int i = 1;
        while(CloudWrapper.USES_WRAPPER_IDS.contains(i)) {
            i++;
        }
        return i;
    }

    private KeepAliveManager keepAliveManager;

    private boolean registered;
    private int wrapperID;
    private String hostAddress;

    public CloudWrapper() {
        this.wrapperID = CloudWrapper.getID();
        this.keepAliveManager = new KeepAliveManager(this);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.hostAddress = ((InetSocketAddress)this.getChannel().remoteAddress()).getAddress().getHostAddress();
        if(this.hostAddress == null || hostAddress.isEmpty()) {
            CloudMaster.LOGGER.warn("Can't resolve host address of new wrapper!");
            return;
        }
        CloudMaster.getInstance().getWrapperCache().register(this);
        this.sendPacket(new PacketOutRegisterWrapper(wrapperID));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        if(!this.isRegistered() && !(packet instanceof PacketInRegisterWrapper)) {
            this.close("Send packet without is registered");
            return;
        }
        super.channelRead0(channelHandlerContext, packet);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if(this.isRegistered())
            CloudMaster.getInstance().getWrapperCache().unregister(this);
    }

    public void register() {
        if(this.isRegistered()) return;
        this.registered = true;
        this.keepAliveManager.startTask();
        CloudMaster.LOGGER.info("Wrapper-" + this.wrapperID + " is now registered.");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
