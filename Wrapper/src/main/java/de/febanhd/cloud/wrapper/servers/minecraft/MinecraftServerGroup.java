package de.febanhd.cloud.wrapper.servers.minecraft;

import de.febanhd.cloud.server.ServerType;
import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.network.masterclient.packets.server.PacketStartServer;
import de.febanhd.cloud.wrapper.servers.AbstractServerGroup;

import java.io.File;
import java.io.IOException;

public class MinecraftServerGroup extends AbstractServerGroup<MinecraftServer> {

    public MinecraftServerGroup(String name, boolean staticServer, int minOnlineServer, int maxMemory, int maxPlayers) {
        super(name, ServerType.MINECRAFT, staticServer, minOnlineServer, maxMemory, maxPlayers);
    }

    @Override
    public void createDir(File dir) throws IOException {
        dir.mkdir();

        File pluginFile = new File(dir, "plugins");
        pluginFile.mkdir();
    }

    @Override
    public MinecraftServer startServer() {
        try {
            MinecraftServer server = new MinecraftServer(this);
            server.initTempFiles(server.getTempDir());
            server.executeProcess();
            this.addServer(server);
            CloudWrapper.getInstance().getMasterNetworkManager().sendPacket(new PacketStartServer(server)); //send start packet to master
            CloudWrapper.LOGGER.info("Start minecraft server of group '" + this.getName() + "' on port " + server.getServerPort());
            return server;
        }catch (IOException e) {
            CloudWrapper.LOGGER.warn("Error while starting server of group " + this.getName());
            e.printStackTrace();
            return null;
        }
    }
}
