package com.xll.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  使用nio实现文件内容复制
 *     nioinput.txt   -->  niooutput.txt
 */
public class MyNio4 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("nioinput.txt");
        FileOutputStream outputStream = new FileOutputStream("niooutput.txt");

        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        // 构建ByteBuffer对象， 先设置512 ，实验 8看看效果
        ByteBuffer buffer = ByteBuffer.allocate(512);

        // read 标识每次读的字节长度， 当没有数据时，返回-1，  如果position == limit ， read = 0；
        while (true) {
            int read = inputStreamChannel.read(buffer);
            System.out.println(read);
            // 思考这里可不可以 == 0， 如果position == limit ， read = 0, 说明将buffer写入channel后没有clear()操作
            if (read < 0) {
                break;
            }
            buffer.flip();
            outputStreamChannel.write(buffer);
            // 每次操作之后需要清空buffer
            buffer.clear();
        }

        /*
         * 关闭channel   FileChannel 会导致文件流被关闭，特别是写较小文件时候,JDK8中说明，“不需要去手动关闭”,代码如下
         * if (this.parent != null) {          // 此处parent就是获取通道的这个文件流
            ((Closeable)this.parent).close();
            }
         */
        outputStreamChannel.close();
        inputStreamChannel.close();
    }
}
