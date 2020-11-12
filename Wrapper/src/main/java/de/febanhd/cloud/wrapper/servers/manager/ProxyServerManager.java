package de.febanhd.cloud.wrapper.servers.manager;

import de.febanhd.cloud.server.ServerInformation;
import de.febanhd.cloud.wrapper.servers.proxy.ProxyServer;
import lombok.Getter;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class ProxyServerManager {

    private CopyOnWriteArrayList<ProxyServer> proxyServers = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<ServerInformation> minecraftServers = new CopyOnWriteArrayList<>();

    public void registerMinecraftServer(ServerInformation serverInformation) {
        this.minecraftServers.add(serverInformation);
        proxyServers.forEach(proxyServer -> {
            proxyServer.addMinecraftServer(serverInformation.getServerName(), serverInformation.getServerIP(), serverInformation.getServerPort());
        });
    }

    public void addProxyServer(ProxyServer proxyServer) {
        this.proxyServers.add(proxyServer);
    }
}
