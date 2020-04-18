package com.xll.netty3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     *
     * @param ctx 执行上下文环境
     * @param  msg 请求消息
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取客户端地址，并大新客户端请求信息
        System.out.println("远程地址" + ctx.channel().remoteAddress() + ", " + msg);

        // 给客户端返回一个消息
        ctx.writeAndFlush("消息已经处理" + UUID.randomUUID());
    }

    /**
     *  异常情况
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /*System.err.println(cause.getMessage());*/
        ctx.close();
    }
}
