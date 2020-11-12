package de.febanhd.cloud.wrapper.servers.proxy;

import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.servers.AbstractServer;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ProxyServer extends AbstractServer<ProxyServerGroup> {

    public static final List<Integer> USED_PORTS = new ArrayList<>();
    private static final int START_PORT = 25565; //TODO: read by config

    private static int getNextProxyPort() {
        int port = START_PORT;
        while(USED_PORTS.contains(port)) {
            port++;
        }
        USED_PORTS.add(port);
        return port;
    }

    public ProxyServer(ProxyServerGroup serverGroup) {
        super(serverGroup, getNextProxyPort());
    }

    public void addMinecraftServer(String serverName, String serverIP, int serverPort) {
        //TODO: send packet to bungee api
    }

    @Override
    public void initTempFiles(File tempDir) throws IOException {
        if(!tempDir.exists()) tempDir.mkdir();
        File configFile = new File(tempDir, "config.yml");
        if(!configFile.exists()) configFile.createNewFile();
        this.writeConfigFile(configFile);

        String fileName = "BungeeCord.jar";
        File versionFile = new File(CloudWrapper.getInstance().getFileManager().getGroupProxyServerFile(), fileName);
        Files.copy(versionFile.toPath(), new File(tempDir, fileName).toPath());
    }

    public void writeConfigFile(File file) {
        BufferedReader reader = null;
        try {
            List<String> lines = new ArrayList<>();
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null) {
                if(!(line.startsWith("connection_throttle") || line.startsWith("- query_port") || line.startsWith("listeners"))) {
                    lines.add(line);
                }
                line = reader.readLine();
            }
            lines.add("listeners:");
            lines.add("- query_port: " + this.getServerPort());
            lines.add("  host: 0.0.0.0:" + this.getServerPort());
            lines.add("connection_throttle: -1");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("");
            fileWriter.flush();
            for(String line2 : lines) {
                fileWriter.write(line2 + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reader != null) {
                try {
                    reader.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Process executeProcess() throws IOException {
        File versionFile = new File(this.getTempDir(), "BungeeCord.jar");
        Process process = new ProcessBuilder("java",
                "-jar",
                "-Xmx" + this.getServerGroup().getMaxMemory() + "M",
                versionFile.getAbsolutePath())
                .directory(this.getTempDir())
                .start();
        this.setProcess(process);
        return process;
    }
}
