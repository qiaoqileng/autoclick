package com.qql.autokick.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUtil {
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAXIMUM_POOL_SIZE;
    private static final int KEEP_ALIVE = 30;
    private static final ThreadFactory sThreadFactory;
    private static final BlockingQueue<Runnable> sPoolWorkQueue;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static ThreadUtil.InternalHandler mHandler;

    public ThreadUtil() {
    }

    public static final void executThread(Runnable run) {
        if (run != null) {
            THREAD_POOL_EXECUTOR.execute(run);
        }

    }

    public static void executeDelayed(Runnable runnable, long delayedTime) {
        executeRunnableDelayed(runnable, delayedTime);
    }

    public static void executeDelayedToUI(Runnable runnable, long delayedTime) {
        executeRunnableDelayedToUI(runnable, delayedTime);
    }

    private static final void executeRunnableDelayed(final Runnable runnable, long delayedTime) {
        getHandler().postDelayed(new Runnable() {
            public void run() {
                ThreadUtil.THREAD_POOL_EXECUTOR.execute(runnable);
            }
        }, delayedTime);
    }

    private static void executeRunnableDelayedToUI(Runnable runnable, long delayedTime) {
        getHandler().postDelayed(runnable, delayedTime);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() != Looper.getMainLooper();
    }

    private static final synchronized Handler getHandler() {
        if (mHandler == null) {
            mHandler = new ThreadUtil.InternalHandler();
        }

        return mHandler;
    }

    static {
        MAXIMUM_POOL_SIZE = CORE_POOL_SIZE * 2;
        sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                String log = "ThreadUtil #" + String.valueOf(this.mCount.getAndIncrement());
                Thread mThread = new Thread(r, log);
                mThread.setPriority(4);
                return mThread;
            }
        };
        sPoolWorkQueue = new SynchronousQueue();
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 30L, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory, new DiscardPolicy());
    }

    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message msg) {
        }
    }
}
