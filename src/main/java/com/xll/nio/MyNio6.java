package com.xll.nio;

import java.nio.ByteBuffer;

/**
 *  测试
 */
public class MyNio6 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("abcdefghigklmnop".getBytes());

        // 获取  [3, 11)的切片， 左开右闭，即  3--10
        buffer.position(3);
        buffer.limit(11);
        System.out.println(buffer);  // [pos=3 lim=11 cap=16]

        ByteBuffer slice = buffer.slice();
        System.out.println(slice);  // [pos=0 lim=8 cap=8]

        // 通过slice 操作，修改slice里面每个值为 'X', 然后查看buffer
        for (int i = 0; i < slice.capacity(); ++i) {
            slice.put((byte) 'X');
        }

        // 打印buffer
        buffer.clear();
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
        // 结果: abcXXXXXXXXlmnop  说明修改slice，buffer也会修改，与subList效果一样。
        System.out.println("\n");
    }
}
