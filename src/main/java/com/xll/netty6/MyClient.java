package com.xll.netty6;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new MyClientHandler());
                        }
                    });
            ChannelFuture connect = bootstrap.connect("localhost", 8899).sync();
            int k = 1;
            for (;k <= 10;) {
                connect.channel().writeAndFlush("AAA k = " + k++);
                Thread.sleep(100);
            }
            connect.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}

