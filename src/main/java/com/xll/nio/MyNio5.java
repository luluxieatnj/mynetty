package com.xll.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  ByteBuffer 之 基本数据类型的  put 和 get
 */
public class MyNio5 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(512);

        buffer.put((byte)16);   // 1
        buffer.putChar('A');    // 1
        buffer.putInt(37);      // 4
        buffer.putDouble(1.234D); // 8
        buffer.putLong(100_000_000L); // 8

        System.out.println(buffer);  // [pos=23 lim=512 cap=512]， 前面已经放到22了

        // 获取，顺序不能错！！
        buffer.flip();
        System.out.println(buffer.get());  // [pos=1 lim=23 cap=512]
        System.out.println(buffer.getChar()); // [pos=3 lim=23 cap=512]
        System.out.println(buffer.getInt()); // [pos=7 lim=23 cap=512]
        System.out.println(buffer.getDouble()); // [pos=15 lim=23 cap=512]
        System.out.println(buffer.getLong());  // [pos=23 lim=23 cap=512]
    }
}
