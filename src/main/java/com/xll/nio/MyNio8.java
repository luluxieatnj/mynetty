package com.xll.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  使用nio实现文件内容复制   使用直接缓存
 */
public class MyNio8 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("nioinput2.txt");
        FileOutputStream outputStream = new FileOutputStream("niooutput2.txt");

        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        // new DirectByteBuffer(capacity);
        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        while (true) {
            int read = inputStreamChannel.read(buffer);
            System.out.println(read);
            if (read < 0) {
                break;
            }
            buffer.flip();
            outputStreamChannel.write(buffer);
            buffer.clear();
        }
        outputStreamChannel.close();
        inputStreamChannel.close();
    }
}
