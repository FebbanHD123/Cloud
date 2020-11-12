package de.febanhd.cloud.master;

import de.febanhd.cloud.master.network.NettyServerBootstrap;
import de.febanhd.cloud.master.wrapper.WrapperCache;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public class CloudMaster {

    @Getter
    private static CloudMaster instance;
    public static final Logger LOGGER = LoggerFactory.getLogger("Master");
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(50);

    public static Executor getExecutor() {
        return EXECUTOR;
    }

    public static void main(String[] args) {
        try {
            new CloudMaster();
        }catch (Exception e) {
            e.printStackTrace();
            //handle exception
        }
    }

    private final NettyServerBootstrap nettyServer;
    private final WrapperCache wrapperCache;
    private String masterID = "";

    private CloudMaster() throws Exception {
        instance = this;
        LOGGER.info("Starting Cloud-Master");
        this.wrapperCache = new WrapperCache();
        this.initMasterID();

        LOGGER.info("Initializing finished");
        this.nettyServer = new NettyServerBootstrap(10000);
    }

    public void initMasterID() throws IOException {
        File file = new File("masterID.txt");
        if(!file.exists()) {
            file.createNewFile();
            String masterID = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
            FileWriter fw = new FileWriter(file);
            fw.write(masterID);
            fw.flush();
            fw.close();
            this.masterID = masterID;
        }else
            this.masterID = new String(Files.readAllBytes(file.toPath()));
        LOGGER.info("Loaded masterID: '" + this.masterID + "'");
    }
}
