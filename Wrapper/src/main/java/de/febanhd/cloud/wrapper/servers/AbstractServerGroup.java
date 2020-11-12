package de.febanhd.cloud.wrapper.servers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.febanhd.cloud.server.ServerType;
import de.febanhd.cloud.wrapper.CloudWrapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Getter
public abstract class AbstractServerGroup<T extends AbstractServer<?>> {

    private final String name;
    private final ServerType serverType;
    private final boolean staticServer;
    private int minOnlineServer;
    private final int maxMemory;
    private final int maxPlayers;
    private File dir;

    private int onlineServerAmount;

    public AbstractServerGroup(String name, ServerType serverType, boolean staticServer, int minOnlineServer, int maxMemory, int maxPlayers) {
        this.name = name;
        this.serverType = serverType;
        this.staticServer = staticServer;
        this.minOnlineServer = minOnlineServer;
        this.maxMemory = maxMemory;
        this.maxPlayers = maxPlayers;
        if(staticServer) {
            this.dir = new File(CloudWrapper.getInstance().getFileManager().getGroupFileStaticServers(), this.name);
        }else {
            this.dir = new File(CloudWrapper.getInstance().getFileManager().getGroupFileDynamicServers(), this.name);
        }
        this.onlineServerAmount = 0;
        try {
            this.createDir(this.dir);
        }catch (Exception e) {
            e.printStackTrace();
            CloudWrapper.LOGGER.warn("Exception while creating dir of server group '" + this.name + "'");
        }
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        SimpleServerGroup simpleServerGroup = this.toSimpleServerGroup();
        return gson.toJson(simpleServerGroup);
    }

    public SimpleServerGroup toSimpleServerGroup() {
        return new SimpleServerGroup(this.name, this.serverType, this.staticServer, this.minOnlineServer, this.maxMemory, this.maxPlayers);
    }

    protected void addServer(T server) {
        this.onlineServerAmount++;
    }

    public abstract void createDir(File dir) throws IOException;

    public abstract T startServer();
}
