package de.febanhd.cloud.master.network;

import de.febanhd.cloud.master.CloudMaster;
import de.febanhd.cloud.master.network.handler.BootstrapChannelInitializerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerBootstrap extends ServerBootstrap {

    private static final boolean EPOLL = Epoll.isAvailable();

    private final int port;

    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;

        this.start();
    }

    public void start() throws InterruptedException {
        CloudMaster.LOGGER.info("Starting Netty-Server on port " + this.port);

        EventLoopGroup eventLoopGroup = NettyServerBootstrap.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        this.group(eventLoopGroup);
        this.channel(NettyServerBootstrap.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
        this.childHandler(new BootstrapChannelInitializerHandler())
                .bind(this.port).sync().channel().closeFuture().syncUninterruptibly();
    }
}
