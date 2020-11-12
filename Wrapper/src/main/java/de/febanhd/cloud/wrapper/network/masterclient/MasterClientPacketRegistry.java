package de.febanhd.cloud.wrapper.network.masterclient;

import de.febanhd.cloud.wrapper.network.keepalive.PacketKeepAlive;
import de.febanhd.cloud.wrapper.network.masterclient.packets.handshake.PacketInRegisterWrapper;
import de.febanhd.cloud.wrapper.network.masterclient.packets.handshake.PacketOutRegisterWrapper;
import de.febanhd.cloud.wrapper.network.Packet;
import de.febanhd.cloud.wrapper.network.masterclient.packets.server.PacketStartServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MasterClientPacketRegistry {

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
        Class<? extends Packet> packetClass = IN_PACKETS.get(id);
        if(packetClass == null) {
            throw new NullPointerException("Couldn't find packet by id " + id);
        }
        return packetClass.newInstance();
    }

    public static int getId(Packet packet) {
        return OUT_PACKETS.indexOf(packet.getClass());
    }
}
