package de.febanhd.cloud.master.wrapper;

import de.febanhd.cloud.master.CloudMaster;
import de.febanhd.cloud.master.network.NetworkManager;

import java.util.LinkedList;

public class WrapperCache {

    private final LinkedList<CloudWrapper> WRAPPER_LIST = new LinkedList<>();

    public void register(CloudWrapper wrapper) {
        CloudMaster.LOGGER.info("Start to register Wrapper-" + wrapper.getWrapperID());
        WRAPPER_LIST.add(wrapper);
    }

    public void unregister(CloudWrapper wrapper) {
        CloudMaster.LOGGER.info("Unregister wrapper (id: " + wrapper.getWrapperID() + ")");
        WRAPPER_LIST.remove(wrapper);
    }

    public CloudWrapper getWrapperByID(int wrapperID) {
        for(CloudWrapper wrapper : this.WRAPPER_LIST) {
            if(wrapper.getWrapperID() == wrapperID) {
                return wrapper;
            }
        }
        return null;
    }

    public CloudWrapper getWrapperByNetworkManager(NetworkManager networkManager) {
        for(CloudWrapper wrapper : this.WRAPPER_LIST) {
            if(wrapper.equals(networkManager)) {
                return wrapper;
            }
        }
        return null;
    }

    public LinkedList<CloudWrapper> getWrappers() {
        return WRAPPER_LIST;
    }
}
