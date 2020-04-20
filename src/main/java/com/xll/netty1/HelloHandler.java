package com.xll.netty1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HelloHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     *  消息处理  。。。。。。。。。。。。。。。。。。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("channelRead0() --------- begin");

        if (msg instanceof HttpRequest) {
            // 创建一个返回客户端的相应消息  helloword
            ByteBuf context = Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8);

            DefaultHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, context);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")   // 文本类型
                    .set(HttpHeaderNames.CONTENT_LENGTH, context.readableBytes());  // 长度
            ctx.writeAndFlush(response);
        }
    }

   /* @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }*/
}
