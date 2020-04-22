package com.xll.nio;


import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *   fileChannel获取锁(排他锁)
 */
public class MyNio10 {
    public static void main(String[] args) throws Exception {

        RandomAccessFile randomAccessFile = new RandomAccessFile("MyNio10.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

//        FileLock lock = fileChannel.lock();  // lock(0L, Long.MAX_VALUE, false);
        FileLock lock = fileChannel.lock(0, 6, true);  // 排他锁

        System.out.println("valid: " + lock.isValid());  // true
        System.out.println("lock type: " + lock.isShared()); // true

        lock.release();  // 释放锁
        fileChannel.close(); // 释放channel

        // 另起一个线程来获得锁
        new Thread(() -> {
            try {
                FileChannel rw = new RandomAccessFile("MyNio10.txt", "rw").getChannel();
                FileLock lock1 = rw.lock();
            } catch (Exception e) {
                // 如果上面的锁不释放，此处获取锁报错  OverlappingFileLockException
                e.printStackTrace();
            } finally {}
        }).start();
    }
}
