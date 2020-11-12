package de.febanhd.cloud.master.network;

import de.febanhd.cloud.master.network.keepalive.PacketKeepAlive;
import de.febanhd.cloud.master.network.packets.Packet;
import de.febanhd.cloud.master.network.packets.handshake.PacketInRegisterWrapper;
import de.febanhd.cloud.master.network.packets.handshake.PacketOutRegisterWrapper;
import de.febanhd.cloud.master.network.packets.server.PacketStartServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketRegistry {

    private static final List<Class<? extends Packet>> OUT_PACKETS = new ArrayList<>();
    private static final List<Class<? extends Packet>> IN_PACKETS = new ArrayList<>();

    static {
        addInPackets(PacketInRegisterWrapper.class, PacketKeepAlive.class, PacketStartServer.class); //add packets to registry
        addOutPackets(PacketOutRegisterWrapper.class, PacketKeepAlive.class, PacketStartServer.class); //add packets to registry
    }

    private static void addInPackets(Class<? extends Packet>... classes) {
        IN_PACKETS.addAll(Arrays.asList(classes));
    }

    private static void addOutPackets(Class<? extends Packet>... classes) {
        OUT_PACKETS.addAll(Arrays.asList(classes));
    }

    public static Packet byId(int id) throws IllegalAccessException, InstantiationException {
        if(IN_PACKETS.size() < id) return null;
        Class<? extends Packet> packetClass = IN_PACKETS.get(id);
        if(packetClass == null) {
            return null;
        }
        return packetClass.newInstance();
    }

    public static int getId(Packet packet) {
        if(OUT_PACKETS.contains(packet.getClass())) {
            return OUT_PACKETS.indexOf(packet.getClass());
        }else {
            return -1;
        }
    }
}
