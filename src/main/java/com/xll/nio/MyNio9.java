package com.xll.nio;


import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *   MappedByteBuffer的使用  在该buffer的操作会直接写道内存中
 */
public class MyNio9 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("MyNio9.txt", "rw"); // mode : "r" 只读; "rw"读写; "rws"读写同步; "rwd"读写分离

        FileChannel fileChannel = accessFile.getChannel();

        // 将此channel文件的区域直接映射到内存中。  READ_ONLY只读   READ_WRITE读写  PRIVATE私有
        // DirectByteBuffer   [pos=0  lim=5 cap=5]
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte)'X');
        mappedByteBuffer.put(3, (byte)'Y');

//        mappedByteBuffer.load();  //  load( )方法会整个文件加载到内存中。
//        mappedByteBuffer.force();  //  该方法会强制将此缓冲区上的任何更改写入映射到永久磁盘存储器上。

//        while (mappedByteBuffer.hasRemaining()) {
//            byte b = mappedByteBuffer.get();
//            System.out.print((char)b);
//        }

        fileChannel.close();
    }
}
