package de.febanhd.cloud.master.network;

import de.febanhd.cloud.master.CloudMaster;
import de.febanhd.cloud.master.network.NetworkManager;
import de.febanhd.cloud.master.network.packets.handshake.PacketInRegisterWrapper;
import de.febanhd.cloud.master.network.keepalive.PacketKeepAlive;
import de.febanhd.cloud.master.network.packets.server.PacketStartServer;
import de.febanhd.cloud.master.wrapper.CloudWrapper;
import de.febanhd.cloud.server.ServerInformation;

import java.net.InetSocketAddress;

public class PacketListener {

    private final NetworkManager networkManager;

    public PacketListener(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public void handleRegisterWrapper(PacketInRegisterWrapper registerWrapperPacket) {
        CloudWrapper wrapper = CloudMaster.getInstance().getWrapperCache().getWrapperByID(registerWrapperPacket.getWrapperID());
        if(wrapper != null) {
            if(CloudMaster.getInstance().getMasterID().equals(registerWrapperPacket.getMasterID())) {
                wrapper.register();
            }else {
                this.networkManager.close("Send invalid masterID");
            }
        }else {
            this.networkManager.close("Wrapper send invalid wrapper-id");
        }
    }

    public void handleKeepAlive(PacketKeepAlive packetKeepAlive) {
        CloudWrapper wrapper = CloudMaster.getInstance().getWrapperCache().getWrapperByNetworkManager(this.networkManager);
        if(wrapper == null) {
            CloudMaster.LOGGER.warn("A keepalive was received but the wrapper could not be detected!");
            return;
        }else {
            wrapper.getKeepAliveManager().handleInPacket(packetKeepAlive);
        }
    }

    public void handleStartServer(PacketStartServer packetStartServer) {
        final CloudWrapper cloudWrapper = CloudMaster.getInstance().getWrapperCache().getWrapperByID(packetStartServer.getServerInformation().getWrapperID());
        ServerInformation serverInformation = packetStartServer.getServerInformation();
        serverInformation.setServerIP(cloudWrapper.getHostAddress());
        CloudMaster.getInstance().getWrapperCache().getWrappers().forEach(wrapper -> {
            wrapper.sendPacket(new PacketStartServer(serverInformation));
        });
        CloudMaster.LOGGER.info("Wrapper-" + cloudWrapper.getWrapperID() + " start the server '" + serverInformation.getServerName() +
                "' on host '" + serverInformation.getServerIP() + ":" + serverInformation.getServerPort() + "'");
    }
}
