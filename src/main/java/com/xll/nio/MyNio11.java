package com.xll.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 *   测试，本地使用命令 nc localhost 8899
 *
 *   关于Buffer的Scattering与Gathering
 *      分散 与 聚合
 *        socketChannel.read(ByteBuffer[] buffers);  可以是数组
 */
public class MyNio11 {
    public static void main(String[] args) throws Exception {
        // 打开SocketChannel服务
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        // 绑定地址
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;
        // 创建3个容器
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            // 已经读取的字节数
            int bytesRead = 0;

            while (bytesRead < messageLength) {
                long read = socketChannel.read(buffers);
                bytesRead += read;

                System.out.println("bytesRead = " + bytesRead);

                Arrays.asList(buffers).stream()
                        .map(buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit())
                        .forEach(System.out::println);
            }

            Arrays.asList(buffers).stream().forEach(b -> b.flip());

            long bytesWritten = 0;
            while (bytesWritten < messageLength) {
                long r = socketChannel.write(buffers);
                bytesWritten += r;
            }

            Arrays.asList(buffers).forEach(b -> b.clear());

            System.out.println("bytesRead: " + bytesRead + ", bytesWritten: " + bytesWritten + ", messageLength: " + messageLength);
            System.out.println("=====================================================================");
        }

    }
}
