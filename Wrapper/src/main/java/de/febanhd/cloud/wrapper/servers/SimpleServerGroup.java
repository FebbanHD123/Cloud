package de.febanhd.cloud.wrapper.servers;

import de.febanhd.cloud.server.ServerType;
import lombok.Getter;

@Getter
public class SimpleServerGroup {

    private final String name;
    private final ServerType serverType;
    private final boolean staticServer;
    private final int minOnlineServer;
    private final int maxMemory;
    private final int maxPlayers;

    public SimpleServerGroup(String name, ServerType serverType, boolean staticServer, int minOnlineServer, int maxMemory, int maxPlayers) {
        this.name = name;
        this.serverType = serverType;
        this.staticServer = staticServer;
        this.minOnlineServer = minOnlineServer;
        this.maxMemory = maxMemory;
        this.maxPlayers = maxPlayers;
    }
}
