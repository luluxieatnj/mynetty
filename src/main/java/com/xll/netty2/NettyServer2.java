package com.xll.netty2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer2 {

    /**
     *  启动项目，  访问  localhost:9111
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            System.out.println("try");
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.AUTO_READ, true)
                    .childHandler(new HelloInitializer());

            ChannelFuture future = bootstrap.bind(9111).sync();
            future.channel().closeFuture().sync();
        } finally {
            System.out.println("childGroup parentGroup 优雅关闭");
            childGroup.shutdownGracefully(); // 优雅关闭
            parentGroup.shutdownGracefully();
        }
    }
}
