package de.febanhd.cloud.wrapper.file;

import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.util.FileUtil;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Getter
public class FileManager {

    private static final String BUNGEECORD_DOWNLOAD_URL = "https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar";
    private static final String SPIGOT_DOWNLOAD_URL = "https://cdn.getbukkit.org/spigot/spigot-1.8.8-R0.1-SNAPSHOT-latest.jar";

    private File groupFile;
    private File groupFileList;
    private File groupFileDynamicServers;
    private File groupFileStaticServers;

    private File groupProxyServerFile;
    private File groupMinecraftServerFile;

    private File versionFile;

    private File tempFile;

    private File configFile;

    private final File wrapperDir;

    private FileDownloader serverPropertiesFileDownloader;

    public FileManager(File wrapperDir) {
        this.wrapperDir = wrapperDir;
        CloudWrapper.LOGGER.info("Creating files");
        this.createDirs();
        this.loadDefaultMinecraftServer(groupMinecraftServerFile);
        this.loadDefaultProxyServer(groupProxyServerFile);
    }

    public void createDirs() {
        this.groupFile = new File(wrapperDir, "groups");
        this.groupFileStaticServers = new File(groupFile, "static");
        this.groupFileDynamicServers = new File(groupFile, "dynamic");
        this.groupFileList = new File(groupFile, "groups");

        this.configFile = new File(wrapperDir, "config");
        this.versionFile = new File(wrapperDir, "versions");
        this.groupProxyServerFile = new File(versionFile, "proxy");
        this.groupMinecraftServerFile = new File(versionFile, "minecraft");

        this.tempFile = new File(wrapperDir, "temp");

        try {
            if(tempFile.exists()) {
                FileUtil.deleteDirectoryAndContents(tempFile.toPath());
                tempFile.mkdir();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tempFile.mkdir();
        configFile.mkdir();
        versionFile.mkdir();
        groupFile.mkdir();
        groupFileStaticServers.mkdir();
        groupFileDynamicServers.mkdir();
        groupFileList.mkdir();
        groupProxyServerFile.mkdir();
        groupMinecraftServerFile.mkdir();
    }

    public void loadDefaultProxyServer(File dir) {
        File proxyFile = new File(dir, "BungeeCord.jar");
        if(!proxyFile.exists()) {
            CloudWrapper.LOGGER.info("Pls wait: Downloading BungeeCord from: " + BUNGEECORD_DOWNLOAD_URL);
            new FileDownloader(BUNGEECORD_DOWNLOAD_URL, "BungeeCord.jar").downloadAndPaste(dir);
        }
    }

    public void loadDefaultMinecraftServer(File dir) {
        File minecraftServerFile = new File(dir, "server.jar");
        if(!minecraftServerFile.exists()) {
            CloudWrapper.LOGGER.info("Pls wait: Downloading spigot from: " + SPIGOT_DOWNLOAD_URL);
            new FileDownloader(SPIGOT_DOWNLOAD_URL, "server.jar").downloadAndPaste(dir);
        }
    }
}
