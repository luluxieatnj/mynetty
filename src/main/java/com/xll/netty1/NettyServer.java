package com.xll.netty1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    /**
     *  启动项目，  访问  localhost:8899
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();  // bosss线程组，只负责接受任务
        NioEventLoopGroup childGroup = new NioEventLoopGroup();    // worker线程组，负责处理任务

        try {
            System.out.println("try................");
            ServerBootstrap bootstrap = new ServerBootstrap();  // 启动
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.AUTO_READ, true)
                    .childHandler(new HelloInitializer());  // 子处理器，channel被注册后，会执行里面的代码

            ChannelFuture future = bootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        } finally {
            System.out.println("childGroup parentGroup 优雅关闭");
            childGroup.shutdownGracefully(); // 优雅关闭
            parentGroup.shutdownGracefully();
        }
    }
}
