package de.febanhd.cloud.wrapper.network;

import de.febanhd.cloud.server.ServerInformation;
import de.febanhd.cloud.server.ServerType;
import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.network.keepalive.PacketKeepAlive;
import de.febanhd.cloud.wrapper.network.masterclient.packets.server.PacketStartServer;

public class PacketListener {

    private final NetworkManager networkManager;

    public PacketListener(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public void handleKeepAlive(PacketKeepAlive packetKeepAlive) {
        PacketKeepAlive responseKeepAlive = new PacketKeepAlive(packetKeepAlive.getTime(), packetKeepAlive.getId());
        this.networkManager.sendPacket(responseKeepAlive);
    }

    public void handleStartServer(PacketStartServer startServerPacket) {
        ServerInformation serverInformation = startServerPacket.getServerInformation();
        String ip = serverInformation.getServerIP();
        if(ip.isEmpty()) {
            CloudWrapper.LOGGER.warn("Can't handle server start from Wrapper-" + serverInformation.getWrapperID() + "! The serverIp is empty!");
            return;
        }
        if(serverInformation.getServerType().equals(ServerType.MINECRAFT)) {
            CloudWrapper.getInstance().getServerGroupManager().getProxyServerManager().registerMinecraftServer(serverInformation);
        }
        if(serverInformation.getWrapperID() != CloudWrapper.getInstance().getWrapperID())
            CloudWrapper.LOGGER.info("Register server '" + serverInformation.getServerName() + "' from Wrapper-" + serverInformation.getWrapperID());
    }
}
