package com.xll.netty5;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 存放各个channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(new DefaultEventExecutor());

    /**
     *  业务处理：
     *      服务端统一接受来自各个客户端的消息，消息分发到各个连接，各个连接只负责消息展示。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 当前与服务器建立的连接（即当前客户端，当前用户channel）
        Channel channel = ctx.channel();

        // ChannelGroup实际是Set，遍历
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                // 将当前连接的消息通知到其他channel
                ch.writeAndFlush("[" + channel.remoteAddress() + "]: " + msg + "\n");
            } else {
                // 通知到自己的消息
                channel.writeAndFlush("[自己]： " + msg + "\n");
            }
        });
    }

    /**
     *  连接创建时，对每个连接进行广播
     *   当 ChannelHandler 添加到 ChannelPipeline 调用
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 加入！\n");
        channelGroup.add(channel);
    }

    /**
     *  连接移除时，对每个连接进行广播
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress()+ " 离开！\n");
        // netty自动移除已经关闭的连接
//        channelGroup.remove(channel);

        System.out.println("Server日志: 当前连接用户人数" + channelGroup.size() + "人");
    }

    /**
     *  channel建立时(激活时)，通知其他以上线用户，
     *          channelActive： 活跃状态，可接收和发送数据。
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        System.out.println("Server日志: " + channel.remoteAddress() + "上线 ！");
    }

    /**
     *  下线时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("Server日志: " + channel.remoteAddress() + "下线 ！");
    }

    /**
     *  异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println(cause.getMessage());
    }
}
