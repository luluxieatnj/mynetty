package com.xll.netty7;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

    /*    pipeline.addLast(new HttpServerCodec()); // 编码解码
        pipeline.addLast(new ChunkedWriteHandler()); // 块处理
        pipeline.addLast(new HttpObjectAggregator(8192)); // netty关于http编程的重要handler,聚合
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws")); // netty关于socket*/
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new WebSocketServerHandler());
    }
}
