package com.xll.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *  nio通信，服务端
 *     实现，服务端一个线程，接受来自不同客户端的连接(nc和java客户端)，将信息推送分发
 *     nio开发步骤比较固定，记住就完了！
 */
public class NioServer {

    /**  定义一个容器，存放来自客户端的连接 */
    private static final Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception{
        /*  step1: 创建服务端Channel对象  */
        // 静态方法获取ServerSocketChannel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        // 服务端，绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8899));

        /* 创建选择器对象，并将Channel对象注册到selector上 */
        // 静态方法获取Selector对象， 一个selector可以处理多种事件，刚开始监听accept事件
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  // 服务端刚开始监听的是accept事件

        /* 服务端循环处理 */
        while (true) {
            try{
                selector.select();  // 当有事件被触发时, 获取该事件的set集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                // 对集合进行遍历
                Iterator<SelectionKey> iter = selectionKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey selectionKey = iter.next();

                    // 判断是接受连接事件还是读事件
                    if (selectionKey.isAcceptable()) {
                        // 由于是accept事件，是ServerSocketChannel注册到选择器上的，肯定可以左向下类型转换
                        ServerSocketChannel channel= (ServerSocketChannel)selectionKey.channel();
                        // 接受连接，返回对象是来自客户端的 SocketChannel
                        SocketChannel clientSocketChannel = channel.accept();

                        // 将这个channel注册到selector上，事件是读事件
                        clientSocketChannel.configureBlocking(false);
                        clientSocketChannel.register(selector, SelectionKey.OP_READ);

                        // 注册完成，放入我们实现创建号的容器中, 使用远程地址作为key，等下用于消息分发
                        String clientChannelKey = clientSocketChannel.getRemoteAddress().toString();
                        clientMap.put(clientChannelKey, clientSocketChannel);
                    } else if (selectionKey.isReadable()) {
                        // 选择器取到读取事件,向下做强制类型转换
                        SocketChannel clientSocketChannel = (SocketChannel)selectionKey.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // todo 此处接受消息应该使用循环，这里暂时默认一次可以接受完数据
                        int read = 0;

                        try {
                            // 如果客户端断开连接，read会读取，但是数据不规范，会抛出异
                            read = clientSocketChannel.read(buffer);
                        } catch (Exception e) {
                            // 处理客户端断开的情况，应该关闭
                            selectionKey.cancel();
                            System.out.println(clientSocketChannel.getRemoteAddress() + "断开连接！！！");
                        }

                        // 处理消息
                        if (read > 0) {
                            // 从buffer读取消息，转换成String
                            buffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            String readMsg = String.valueOf(charset.decode(buffer).array());
                            System.out.println("来自客户端: " + clientSocketChannel.getRemoteAddress() + readMsg);

                            // 将消息分发出去
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                SocketChannel channel = entry.getValue();
//                                ByteBuffer writeBuffer = charset.encode("From " + clientSocketChannel.getRemoteAddress() + ": " + readMsg);

                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                // todo 解决中文乱码问题
                                writeBuffer.put(("From " + clientSocketChannel.getRemoteAddress() + ": " + readMsg).getBytes());
                                writeBuffer.flip();
                                channel.write(writeBuffer);
                            }
                        }
                    }
                    // 处理完该事件，记得remove，重复处理会报错
                    iter.remove();
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
