package com.xll.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  想文件中写入一句话
 */
public class MyNio3 {
    public static void main(String[] args) throws Exception{
        FileOutputStream fileOutputStream = new FileOutputStream("MyNio3.txt");
        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        buffer.put("hello world, welcome nanjing!".getBytes());

        buffer.flip();
        channel.write(buffer);
        channel.close();
        fileOutputStream.close();
    }
}
