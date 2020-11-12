package de.febanhd.cloud.master.network.handler;

import de.febanhd.cloud.master.CloudMaster;
import de.febanhd.cloud.master.network.NettyServerBootstrap;
import de.febanhd.cloud.master.network.NetworkManager;
import de.febanhd.cloud.master.wrapper.CloudWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

import java.net.InetSocketAddress;

public class BootstrapChannelInitializerHandler extends ChannelInitializer<Channel> {

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new PacketEncoder());
        pipeline.addLast(new PacketDecoder());
        pipeline.addLast(new CloudWrapper());
    }

    private String getHostAdress(Channel channel) {
        String host = "";
        try {
            InetSocketAddress address = (InetSocketAddress)channel.remoteAddress();
            host = address.getAddress().getHostAddress();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return host;
    }

    private int getPort(Channel channel) {
        int port = -1;
        try {
            InetSocketAddress address = (InetSocketAddress)channel.remoteAddress();
            port = address.getPort();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return port;
    }
}
