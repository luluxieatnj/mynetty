package com.xll.nio;


import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 *   服务端一个线程，实现处理来自多个客户端的连接
 *   Selector channel  byteBuffer 使用
 *
 *   -- 测试， 在Windows下， 打开命令窗口，输入 : nc localhost 5000即可测试  (前提，已经配置nc环境变量)
 */
public class MyNio12 {
    public static void main(String[] args) throws Exception {
        int[] ports = {5000, 5001, 5002, 5003, 5004};

        // 创建Selector对象
        Selector selector = Selector.open();

        for (int port : ports) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); // 配置 非阻塞
            ServerSocket socket = serverSocketChannel.socket();
            socket.bind(new InetSocketAddress(port));  // 绑定端口

            // 注册， 实现通道 和 选择器之间的关联关系
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  // 最开始处理接受请求
            System.out.println("监听端口: " + port);
        }

        while (true) {
            int select = selector.select();
            System.out.println("select numbers: " + select);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 获取已经注册的key集合
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel acceptChannel = serverSocketChannel.accept();
                    acceptChannel.configureBlocking(false);
                    acceptChannel.register(selector, SelectionKey.OP_READ);

                    iter.remove(); // 不能缺失
                    System.out.println("获取连接" + serverSocketChannel);
                } else if (selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    // 读取channel中数据
                    ByteBuffer buffer = ByteBuffer.allocate(512);

                    int readByte = 0;  // 容器，看看读取了多少数据
                    while (true) {
                        buffer.clear();
                        int read = socketChannel.read(buffer);
                        if (read <= 0) break;
                        readByte += read;
                        buffer.flip();
                        // 再给他写回去
                        socketChannel.write(buffer);
                    }
                    System.out.println("读取数据readByte = " + readByte);
                    iter.remove();
                }
            }

        }
    }
}
