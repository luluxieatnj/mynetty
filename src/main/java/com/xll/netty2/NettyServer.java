package com.xll.netty2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *  netty 服务端
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //NioEventLoopGroup可以看做是一个线程池,parentGroup用来接收所有请求,childGroup用来处理具体IO任务
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();//用来处理服务器端接受客户连接
        NioEventLoopGroup childGroup = new NioEventLoopGroup(); //用来进行网络通信(网络读写)
        ServerBootstrap bootstrap = new ServerBootstrap();      //创建服务器通道配置的辅助工具类
        bootstrap.group(parentGroup,childGroup)                 //配置每个NioEventLoopGroup的用途
                .channel(NioServerSocketChannel.class)          //指定Nio模式为Server模式
                .option(ChannelOption.SO_BACKLOG,1024)    //指定tcp缓冲区
                .option(ChannelOption.SO_SNDBUF,10*1024)  //指定发送缓冲区大小
                .option(ChannelOption.SO_RCVBUF,10*1024)  //指定接收缓冲区大小
                .option(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)//是否保持连接,默认true
                .childHandler(new ChannelInitializer<SocketChannel>() {//具体的数据接收方法
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {     //添加ChannelHandler,handler用来自定义消息处理逻辑
                        sc.pipeline().addLast(new ServerHandler());//其实可以添加多个Handler实例对象
                    }
                });

        ChannelFuture cfuture = bootstrap.bind(8899).sync();//异步绑定端口
        cfuture.channel().closeFuture().sync();//阻塞程序,等待关闭

        System.out.println("server close----------1");

        parentGroup.shutdownGracefully();//关闭应用
        childGroup.shutdownGracefully();
    }
}
