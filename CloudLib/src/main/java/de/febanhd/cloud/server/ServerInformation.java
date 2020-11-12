package de.febanhd.cloud.server;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerInformation {

    private String serverName;
    private String serverIP;
    private int serverPort;
    private int wrapperID;
    private ServerType serverType;

    public ServerInformation(String serverName, String serverIP, int serverPort, int wrapperID, ServerType serverType) {
        this.serverName = serverName;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.wrapperID = wrapperID;
        this.serverType = serverType;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static ServerInformation fromString(String string) {
        try {
            return new Gson().fromJson(string, ServerInformation.class);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while resolving serverinformation!");
            return null;
        }
    }
}
