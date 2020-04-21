package com.xll.nio;

import java.nio.ByteBuffer;

/**
 *  Buffer内部维护了4个指针。
 *  // Invariants: mark <= position <= limit <= capacity
 *     private int mark = -1;    // 标记位
 *     private int position = 0; // 位置指针
 *     private int limit;        // 限制位
 *     private int capacity;     // 容量
 */
public class MyNio0 {
    public static void main(String[] args) {
        // 构造一个ByteBuffer， 容量为16
        ByteBuffer buffer = ByteBuffer.allocate(16);  // [pos=0 lim=16 cap=16]

        // 放入'a' 'b' 'c' 'd' 'e'
        buffer.put("abcde".getBytes());  // [pos=5 lim=16 cap=16]

        // 模式转换  写 <=> 读
        buffer.flip();  // [pos=0 lim=5 cap=16]

        // 读取一个
        char c = (char) buffer.get(); // [pos=1 lim=5 cap=16]
         c = (char) buffer.get(); // [pos=2 lim=5 cap=16]

        buffer.flip();  // [pos=0 lim=2 cap=16]

        buffer.put("xy".getBytes()); // [pos=2 lim=2 cap=16]

//        buffer.put("zaa".getBytes()); // 报错 BufferOverflowException ，超过limit

        System.out.println(buffer);
    }
}
