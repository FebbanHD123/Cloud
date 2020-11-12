package de.febanhd.cloud.wrapper.network.wrapperserver;

import de.febanhd.cloud.wrapper.network.wrapperserver.handler.ServerBootstrapChannelInitializerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerBootstrap extends ServerBootstrap {

    public static final Logger LOGGER = LoggerFactory.getLogger(NettyServerBootstrap.class);
    private static final boolean EPOLL = Epoll.isAvailable();

    private final int port;

    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;

        this.start();
    }

    public void start() throws InterruptedException {
        LOGGER.info("Starting Netty-Server on port " + this.port);

        EventLoopGroup eventLoopGroup = NettyServerBootstrap.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        this.group(eventLoopGroup);
        this.channel(NettyServerBootstrap.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
        this.childHandler(new ServerBootstrapChannelInitializerHandler());
        this.bind(this.port).sync().channel().closeFuture().syncUninterruptibly();

    }
}
