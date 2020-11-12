package de.febanhd.cloud.wrapper.servers;

import de.febanhd.cloud.wrapper.CloudWrapper;
import de.febanhd.cloud.wrapper.servers.proxy.ProxyServer;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class AbstractServer<T extends AbstractServerGroup<?>> {

    private static final List<Integer> USED_PORTS = new ArrayList<>();
    private static final int START_PORT = 10001; //TODO: read by config

    private static int getNextPort() {
        int port = START_PORT;
        while(USED_PORTS.contains(port) || ProxyServer.USED_PORTS.contains(port)) {
            port++;
        }
        USED_PORTS.add(port);
        return port;
    }

    private final T serverGroup;
    private final int serverPort;
    private final File tempDir;
    private final String serverName;
    @Setter
    private Process process;

    protected AbstractServer(T serverGroup) {
        this.serverGroup = serverGroup;
        this.serverPort = getNextPort();
        this.serverName = this.serverGroup.getName() + "-" + serverGroup.getOnlineServerAmount();
        this.tempDir = this.initTempDir();
    }

    protected AbstractServer(T serverGroup, int port) {
        this.serverGroup = serverGroup;
        this.serverPort = port;
        this.serverName = this.serverGroup.getName() + "-" + serverGroup.getOnlineServerAmount();
        this.tempDir = this.initTempDir();
    }

    public abstract void initTempFiles(File tempDir) throws IOException;

    public abstract Process executeProcess() throws IOException;

    private File initTempDir() {
        File templateTempFile = new File(CloudWrapper.getInstance().getFileManager().getTempFile().getAbsoluteFile() + "/" + serverGroup.getName());
        if(!templateTempFile.exists())
            templateTempFile.mkdir();

        File tempDir = new File(templateTempFile, this.serverName + "_" + UUID.randomUUID().toString());
        if(!tempDir.exists())
            tempDir.mkdir();
        return tempDir;
    }
}
