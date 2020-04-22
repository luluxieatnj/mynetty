package com.xll.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 *  Buffer内部维护了4个指针。
 *  // Invariants: mark <= position <= limit <= capacity
 *     private int mark = -1;   // 标记位
 *     private int position = 0;// 位置指针
 *     private int limit;       // 限制位
 *     private int capacity;
 *
 *     // 容量
 */
public class MyNio1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 10; i++) {
            buffer.put(random.nextInt(20));
        }

        System.out.println(buffer);
        // 从写模式 切换到 读模式
        buffer.flip();
        System.out.println(buffer);

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        System.out.println(buffer);
    }
}
