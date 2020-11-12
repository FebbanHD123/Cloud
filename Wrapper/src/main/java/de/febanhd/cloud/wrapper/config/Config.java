package de.febanhd.cloud.wrapper.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.febanhd.cloud.wrapper.CloudWrapper;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

@Getter
public class Config {

    private final String masterHostAddress;
    private final int masterPort;

    private final int startPortProxy;
    private final int startPortMinecraftServer;

    public Config(String masterHostAddress, int masterPort, int startPortProxy, int startPortMinecraftServer) {
        this.masterHostAddress = masterHostAddress;
        this.masterPort = masterPort;
        this.startPortProxy = startPortProxy;
        this.startPortMinecraftServer = startPortMinecraftServer;
    }

    public static Config loadConfig() throws IOException {
        File file = new File(CloudWrapper.getInstance().getFileManager().getConfigFile(), "config.yml");
        if(file.exists()) {
            String json = new String(Files.readAllBytes(file.toPath()));
            Config config = new Gson().fromJson(json, Config.class);
            return config;
        }else {
            file.createNewFile();
            Config config = new Config("localhost", 10000, 25565, 11000);
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(config);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
            return config;
        }
    }
}
