package de.febanhd.cloud.wrapper.network.wrapperserver.handler;

import de.febanhd.cloud.wrapper.network.wrapperserver.NettyServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

import java.net.InetSocketAddress;

public class ServerBootstrapChannelInitializerHandler extends ChannelInitializer<Channel> {


    protected void initChannel(Channel channel) throws Exception {
        NettyServerBootstrap.LOGGER.info(this.getHostAdress(channel) + ":" + this.getPort(channel) + " connected.");
        //TODO: add decoder...
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
