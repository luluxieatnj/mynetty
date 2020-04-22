package com.xll.nio;


import java.nio.ByteBuffer;

/**
 *   只读操作
 */
public class MyNio7 {
    public static void main(String[] args) throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(64);

        System.out.println(buffer.isReadOnly());  //false

        // 注意会生成一个新的对象  return new HeapByteBufferR(...);
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        System.out.println(readOnlyBuffer.isReadOnly());
    }
}
