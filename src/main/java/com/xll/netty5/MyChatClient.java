package com.xll.netty5;

import com.xll.netty4.MyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyChatClient {
    public static void main(String[] args) throws Exception{
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyChatClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            Channel channel = channelFuture.channel();
//            channelFuture.channel().closeFuture().sync();  // 不关闭
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                // 从控制台读取消息，将其发送给服务器，服务器负责消息分发
                channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
            }
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
