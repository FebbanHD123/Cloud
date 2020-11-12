package de.febanhd.cloud.wrapper.servers.minecraft;

import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.servers.AbstractServer;
import de.febanhd.cloud.wrapper.util.FileUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MinecraftServer extends AbstractServer<MinecraftServerGroup> {

    protected MinecraftServer(MinecraftServerGroup serverGroup, int serverPort) {
        super(serverGroup, serverPort);
    }

    public MinecraftServer(MinecraftServerGroup serverGroup) {
        super(serverGroup);
    }

    @Override
    public void initTempFiles(File tempDir) throws IOException {
        FileUtil.copyDirToDir(this.getServerGroup().getDir(), this.getTempDir());
        File eulaFile = new File(tempDir, "eula.txt");
        eulaFile.createNewFile();
        this.writeEulaFile(eulaFile);

        File serverPropertiesFile = new File(tempDir, "server.properties");
        if (!serverPropertiesFile.exists())
            serverPropertiesFile.createNewFile();
        this.writeServerProperties(serverPropertiesFile);

        String fileName = "server.jar";
        File versionFile = new File(CloudWrapper.getInstance().getFileManager().getGroupMinecraftServerFile(), fileName);
        Files.copy(versionFile.toPath(), new File(tempDir, fileName).toPath());
    }

    public void writeEulaFile(File file) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.write("eula=true");
        fw.flush();
        fw.close();
    }

    //takes the mormal server.properties and excanges the onlinemode value for onlinemode false
    public void writeServerProperties(File file) {
        BufferedReader reader = null;
        try {
            List<String> lines = new ArrayList<>();
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null) {
                if(!(line.startsWith("online-mode"))) {
                    lines.add(line);
                }
                line = reader.readLine();
            }
            lines.add("online-mode=false");
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
        File versionFile = new File(this.getTempDir(), "server.jar");
        Process process = new ProcessBuilder("java",
                "-jar",
                "-Xmx" + this.getServerGroup().getMaxMemory() + "M",
                versionFile.getAbsolutePath(),
                "-p" + this.getServerPort(),
                "-s" + this.getServerGroup().getMaxPlayers()) //spigot args https://www.spigotmc.org/wiki/start-up-parameters/
                .directory(this.getTempDir()).start();
        this.setProcess(process);
        return process;
    }
}
