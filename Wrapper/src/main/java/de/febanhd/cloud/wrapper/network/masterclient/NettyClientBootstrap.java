package de.febanhd.cloud.wrapper.network.masterclient;

import de.febanhd.cloud.wrapper.network.NetworkManager;
import de.febanhd.cloud.wrapper.network.handler.PacketDecoder;
import de.febanhd.cloud.wrapper.network.handler.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;

public class NettyClientBootstrap {

    public static final boolean EPOLL = Epoll.isAvailable();

    @Getter
    private NetworkManager networkManager;

    public NettyClientBootstrap(String host, int port) {
        EventLoopGroup eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            Channel channel = new Bootstrap()
                    .group(eventLoopGroup)
                    .channel(EPOLL ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();

                            pipeline.addLast(new PacketDecoder());
                            pipeline.addLast(new PacketEncoder());
                            pipeline.addLast(networkManager = new NetworkManager());

                        }
                    }).connect(host, port).sync().channel();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
