package com.xll.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  使用nio读取文件
 */
public class MyNioTest2 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("MyNioTest2.txt");
        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        channel.read(buffer);

        buffer.flip();

        // 或者 (buffer.remaining() > 0)
        while (buffer.hasRemaining()) {
            System.out.print((char)buffer.get());
        }
        channel.close();
        fileInputStream.close();
    }
}
