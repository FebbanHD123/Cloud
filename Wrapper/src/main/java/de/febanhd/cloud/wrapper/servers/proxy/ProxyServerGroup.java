package de.febanhd.cloud.wrapper.servers.proxy;

import de.febanhd.cloud.server.ServerType;
import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.network.masterclient.packets.server.PacketStartServer;
import de.febanhd.cloud.wrapper.servers.AbstractServerGroup;

import java.io.File;
import java.io.IOException;

public class ProxyServerGroup extends AbstractServerGroup<ProxyServer> {

    public ProxyServerGroup(String name, boolean staticServer, int minOnlineServer, int maxMemory, int maxPlayers) {
        super(name, ServerType.PROXY, staticServer, minOnlineServer, maxMemory, maxPlayers);
    }

    @Override
    public void createDir(File dir) {
        dir.mkdir();
        new File(dir, "plugins").mkdir();
    }

    @Override
    public ProxyServer startServer() {
        try {
            ProxyServer server = new ProxyServer(this);
            server.initTempFiles(server.getTempDir());
            server.executeProcess();
            this.addServer(server);
            CloudWrapper.getInstance().getServerGroupManager().getProxyServerManager().addProxyServer(server); //add proxy server to proxy cache
            CloudWrapper.getInstance().getMasterNetworkManager().sendPacket(new PacketStartServer(server)); //send start packet to master
            CloudWrapper.LOGGER.info("Start proxy server of group '" + this.getName() + "' on port " + server.getServerPort());
            return server;
        }catch (IOException e) {
            CloudWrapper.LOGGER.warn("Error while starting server of group " + this.getName());
            e.printStackTrace();
            return null;
        }
    }
}
