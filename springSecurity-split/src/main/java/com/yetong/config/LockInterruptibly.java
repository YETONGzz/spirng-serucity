package com.yetong.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptibly {

    private static final Logger log = LoggerFactory.getLogger(LockInterruptibly.class);
    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("t1尝试获取锁");
            try {
                if (!reentrantLock.tryLock(5, TimeUnit.SECONDS)) {
                    log.debug("没有获取到锁");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                log.debug("t1 获取到锁");
            } finally {
                reentrantLock.unlock();
            }

        }, "t1");

        //主线程上锁
        reentrantLock.lock();
        log.debug("main 获取到锁");
        t1.start();

        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

}
