package de.febanhd.cloud.wrapper.servers.manager;

import com.google.gson.Gson;
import de.febanhd.cloud.server.ServerType;
import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.servers.AbstractServerGroup;
import de.febanhd.cloud.wrapper.servers.SimpleServerGroup;
import de.febanhd.cloud.wrapper.servers.minecraft.MinecraftServerGroup;
import de.febanhd.cloud.wrapper.servers.proxy.ProxyServerGroup;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class ServerGroupManager {

    private final CopyOnWriteArrayList<AbstractServerGroup<?>> serverGroups;

    private ProxyServerManager proxyServerManager;

    public ServerGroupManager() {
        this.serverGroups = new CopyOnWriteArrayList<>();
        this.proxyServerManager = new ProxyServerManager();
        try {
            this.loadServerGroups();
        }catch (Exception e) {
            CloudWrapper.LOGGER.warn("Error while loading server groups");
            e.printStackTrace();
        }
    }

    public AbstractServerGroup<?> createServerGroup(ServerType serverType, String name, boolean staticServer, int minOnlineServers, int maxMemory, int maxPlayers) {
        CloudWrapper.LOGGER.info("Create server-group " + name + ". With arguments:" +
                " type=" + serverType.toString() + ", static=" + staticServer, ", minOnlineServers=" + minOnlineServers + ", maxMemory=" + maxMemory + "MB, maxPlayers=" + maxPlayers);
        if(serverType.equals(ServerType.MINECRAFT)) {
            MinecraftServerGroup serverGroup = new MinecraftServerGroup(name, staticServer, minOnlineServers, maxMemory, maxPlayers);
            this.addServerGroup(serverGroup);
            for(int i = 0; i < minOnlineServers; i++) {
                serverGroup.startServer();
            }
            this.saveToFile(serverGroup);
            return serverGroup;
        } else if (serverType.equals(ServerType.PROXY)) {
            ProxyServerGroup serverGroup = new ProxyServerGroup(name, staticServer, minOnlineServers, maxMemory, maxPlayers);
            this.addServerGroup(serverGroup);
            for(int i = 0; i < minOnlineServers; i++) {
                serverGroup.startServer();
            }
            this.saveToFile(serverGroup);
            return serverGroup;
        }
        return null;
    }

    private void addServerGroup(AbstractServerGroup<?> serverGroup) {
        CloudWrapper.LOGGER.info("Added server group '" + serverGroup.getName() + "' (" + serverGroup.getServerType().toString() + ") to cache.");
        this.serverGroups.add(serverGroup);
    }

    private void loadServerGroups() throws IOException {
        CloudWrapper.LOGGER.info("Loading server groups");
        File[] serverGroupFiles = CloudWrapper.getInstance().getFileManager().getGroupFileList().listFiles();
        if(serverGroupFiles == null || serverGroupFiles.length == 0) {
            CloudWrapper.LOGGER.info("No server groups were loaded. Create one with the command 'creategroup'.");
            return;
        }
        for(File file : serverGroupFiles) {
            if(file.getName().endsWith(".json")) {
                this.loadServerGroupFromFile(file);
            }
        }
    }

    private void loadServerGroupFromFile(File file) throws IOException {
        String json = new String(Files.readAllBytes(file.toPath()));
        Gson gson = new Gson();
        SimpleServerGroup simpleServerGroup = gson.fromJson(json, SimpleServerGroup.class);
        AbstractServerGroup<?> serverGroup;
        if(simpleServerGroup.getServerType().equals(ServerType.MINECRAFT)) {
            serverGroup = new MinecraftServerGroup(simpleServerGroup.getName(), simpleServerGroup.isStaticServer(),
                    simpleServerGroup.getMinOnlineServer(), simpleServerGroup.getMaxMemory(), simpleServerGroup.getMaxPlayers());
        }else {
            serverGroup = new ProxyServerGroup(simpleServerGroup.getName(), simpleServerGroup.isStaticServer(), simpleServerGroup.getMinOnlineServer(),
                    simpleServerGroup.getMaxMemory(), simpleServerGroup.getMaxPlayers());
        }
        this.addServerGroup(serverGroup);
    }

    public void saveToFile(AbstractServerGroup<?> serverGroup) {
        try {
            File file = new File(CloudWrapper.getInstance().getFileManager().getGroupFileList(), serverGroup.getName() + ".json");
            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(serverGroup.toString());
            fileWriter.flush();
        }catch (IOException e) {
            e.printStackTrace();
            CloudWrapper.LOGGER.warn("Error while creating the file for the group '" + serverGroup.getName() + "'");
        }
    }

    public void startServerGroupServers() {
        CloudWrapper.LOGGER.info("Starting minecraft servers");
        this.serverGroups.forEach(this::startMinAmountOfServer);
    }

    public void startMinAmountOfServer(AbstractServerGroup<?> serverGroup) {
        for(int i = 0; i < serverGroup.getMinOnlineServer(); i++) {
            serverGroup.startServer();
        }
    }
}
