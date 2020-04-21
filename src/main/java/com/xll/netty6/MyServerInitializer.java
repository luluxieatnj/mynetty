package com.xll.netty6;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 心跳机制  必须放在第一个
        // case1：当服务端在5秒内没有接收(read)某个channel到消息，触发READER_IDLE事件
        // case2：当服务端在5秒内没有发送(write)消息，触发WRITER_IDLE事件
        // case3：当服务端在5秒内即不接受也不发送，触发ALL_IDLE事件
        pipeline.addLast(new IdleStateHandler(5, 7, 10, TimeUnit.SECONDS));

        pipeline.addLast(new MyServerHandler());
    }
}
