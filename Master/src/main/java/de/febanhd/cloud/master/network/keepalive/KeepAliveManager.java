package de.febanhd.cloud.master.network.keepalive;

import de.febanhd.cloud.master.CloudMaster;
import de.febanhd.cloud.master.network.NetworkManager;
import de.febanhd.cloud.master.wrapper.CloudWrapper;
import de.febanhd.cloud.master.wrapper.WrapperCache;

public class KeepAliveManager {

    private final CloudWrapper wrapper;

    private boolean waitingForResponse;
    private long lastPacketSend;
    private int currentPacketID;

    public KeepAliveManager(CloudWrapper wrapper) {
        this.wrapper = wrapper;
        this.waitingForResponse = false;
        this.lastPacketSend = -1;
    }

    public void startTask() {
        CloudMaster.getExecutor().execute(() -> {
            while(wrapper.isRegistered()) {
                if(!waitingForResponse) {
                    try {
                        Thread.sleep(5000);
                        this.lastPacketSend = System.currentTimeMillis();
                        PacketKeepAlive packetKeepAlive = new PacketKeepAlive(this.lastPacketSend);
                        this.currentPacketID = packetKeepAlive.getId();
                        wrapper.sendPacket(packetKeepAlive);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if (this.lastPacketSend + 30000 < System.currentTimeMillis()) {
                    this.timeOut();
                }
            }
        });
    }

    public void handleInPacket(PacketKeepAlive packetKeepAlive) {
        if(packetKeepAlive.getId() != this.currentPacketID)
            this.timeOut();
        else {
            this.waitingForResponse = false;
        }
    }

    private void timeOut() {
        this.wrapper.close("Wrapper Timeout");
        CloudMaster.getInstance().getWrapperCache().unregister(this.wrapper);
    }
}
