package de.febanhd.cloud.wrapper;

import de.febanhd.cloud.wrapper.command.CommandHandler;
import de.febanhd.cloud.wrapper.config.Config;
import de.febanhd.cloud.wrapper.exception.InvalidMasterIDException;
import de.febanhd.cloud.wrapper.file.FileManager;
import de.febanhd.cloud.wrapper.network.NetworkManager;
import de.febanhd.cloud.wrapper.network.masterclient.NettyClientBootstrap;
import de.febanhd.cloud.wrapper.network.masterclient.packets.handshake.PacketOutRegisterWrapper;
import de.febanhd.cloud.wrapper.servers.manager.ServerGroupManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;

@Getter
public class CloudWrapper {

    public static final Logger LOGGER = LoggerFactory.getLogger("Wrapper");
    @Getter
    private static CloudWrapper instance;

    public static void main(String[] args) throws UnknownHostException {
        new CloudWrapper();
    }

    private NetworkManager masterNetworkManager;
    private String masterID;

    private int wrapperID;
    private boolean registered;

    private ServerGroupManager serverGroupManager;
    private FileManager fileManager;
    private CommandHandler commandHandler;

    private Config config;

    private CloudWrapper() {
        instance = this;
        try {
            this.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                stop();
            }));
        }catch (Exception e) {
            e.printStackTrace();
            //handle exception
        }
    }

    private void start() throws Exception {
        LOGGER.info("Starting Wrapper");
        this.fileManager = new FileManager(new File("."));
        this.config = Config.loadConfig();
        this.commandHandler = new CommandHandler();

        this.registered = false;

        this.wrapperID = -1;
        this.masterID = this.readMasterID();
        if(masterID == null)
            throw new InvalidMasterIDException("Invalid MasterID. Put the id of your cloud-master in the masterID.txt");

        this.serverGroupManager = new ServerGroupManager();
        this.connectToMaster(this.config.getMasterHostAddress(), this.config.getMasterPort());
    }

    public void connectToMaster(String host, int port) {
        LOGGER.info("Connecting to master (" + host + ":" + port + ")");
        NettyClientBootstrap nettyClientBootstrap = new NettyClientBootstrap(host, port);
        this.masterNetworkManager = nettyClientBootstrap.getNetworkManager();
    }

    public void registerWrapper(int wrapperID) {
        if(this.registered) {
            LOGGER.warn("Wrapper received a register packet but its already registered!");
            return;
        }
        this.wrapperID = wrapperID;
        LOGGER.info("Received wrapperID from master and finish registration.");
        this.masterNetworkManager.sendPacket(new PacketOutRegisterWrapper(this.masterID, wrapperID));
        this.serverGroupManager.startServerGroupServers();
        this.commandHandler.startInputThread();
    }

    public String readMasterID() throws IOException {
        File file = new File("masterID.txt");
        if(!file.exists()) {
            file.createNewFile();
            return null;
        }
        String masterID = new String(Files.readAllBytes(file.toPath()));
        return masterID;
    }

    public void stop() {
        LOGGER.info("Stopping Wrapper...");
    }
}
