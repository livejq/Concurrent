package threadPool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author livejq
 * @since 2019/9/1
 */
public class BasicThreadPool implements ThreadPool {
    private Thread maintenance;
    private int initSize;
    private int maxSize;
    private int coreSize;
    private int activeCount;
    private ThreadFactory threadFactory;
    private volatile boolean isShutdown = false;
    // 任务队列
    private RunnableQueue runnableQueue;
    // 工作线程队列
    private Queue<ThreadTask> threadQueue = new ArrayDeque<>();
    private static final DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();
    private long keepAliveTime;
    private TimeUnit timeUnit;

    public BasicThreadPool(int initSize, int coreSize, int maxSize, int queueSize, ThreadFactory threadFactory, DenyPolicy denyPolicy, int keepAliveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        // 线程池的维护
        this.maintenance = new Thread(() -> {
            while (!isShutdown && !Thread.currentThread().isInterrupted()) {
                try {
                    this.timeUnit.sleep(this.keepAliveTime);
                } catch (InterruptedException e) {
                    this.isShutdown = true;
                    break;
                }

                synchronized (this) {
                    if (runnableQueue.size() > 0 && activeCount < coreSize) {
                        for (int i = initSize; i < coreSize; i++) {
                            newThread();
                        }
                        continue;
                    }
                    if (runnableQueue.size() > 0 && activeCount < maxSize) {
                        for (int i = coreSize; i < maxSize; i++) {
                            newThread();
                        }
                    }
                    if (runnableQueue.size() == 0 && activeCount > coreSize) {
                        for (int i = coreSize; i < activeCount; i++) {
                            removeThread();
                        }
                    }
                }
            }
        });
        if(isIllegalParam()) {
            init();
        }
    }

    public BasicThreadPool(int initSize, int coreSize, int maxSize, int queueSize) {
        this(initSize, coreSize, maxSize, queueSize, DEFAULT_THREAD_FACTORY, DEFAULT_DENY_POLICY, 10, TimeUnit.SECONDS);
    }

    private void init() {
        maintenance.start();
        for (int i = 0; i < initSize; i++) {
            newThread();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (this.isShutdown) throw new IllegalStateException("此线程池已销毁！");
        if (!isIllegalParam()) {
            throw new IllegalStateException("参数错误!");
        }
        runnableQueue.offer(runnable);
    }

    private void newThread() {
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(internalTask, thread);
        threadQueue.offer(threadTask);
        activeCount++;
        thread.start();
    }

    private void removeThread() {
        ThreadTask threadTask = threadQueue.remove();
        threadTask.internalTask.stop();
        activeCount--;
    }

    @Override
    public void shutdown() {
        synchronized (this) {
            if (isShutdown) return;
            this.isShutdown = true;
            threadQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
            this.maintenance.interrupt();
        }
    }

    @Override
    public int getInitSize() {
        if (this.isShutdown) throw new IllegalStateException("此线程池已销毁！");
        return initSize;
    }

    @Override
    public int getMaxSize() {
        if (this.isShutdown) throw new IllegalStateException("此线程池已销毁！");
        return maxSize;
    }

    @Override
    public int getCoreSize() {
        if (this.isShutdown) throw new IllegalStateException("此线程池已销毁！");
        return coreSize;
    }

    @Override
    public int getQueueSize() {
        if (this.isShutdown) throw new IllegalStateException("此线程池已销毁！");
        return runnableQueue.size();
    }

    @Override
    public int getActiveSize() {
        if (this.isShutdown) throw new IllegalStateException("此线程池已销毁！");
        return activeCount;
    }

    @Override
    public boolean isIllegalParam() {
        if(initSize < 0 || coreSize < 0 || maxSize < 0) {
            System.out.println("参数非负数！");

            return false;
        }
        if(initSize > maxSize || coreSize < initSize || coreSize > maxSize) {
            System.out.println("参数不合法！");

            return false;
        }
        return true;
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    private static class ThreadTask {
        public ThreadTask(InternalTask internalTask, Thread thread) {
            this.internalTask = internalTask;
            this.thread = thread;
        }

        private InternalTask internalTask;
        private Thread thread;

        public Thread getThread() {
            return thread;
        }

        public InternalTask getInternalTask() {
            return internalTask;
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);
        private static final ThreadGroup group = new ThreadGroup("MyThreadPool-" + GROUP_COUNTER.getAndDecrement());
        private static final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group, runnable, "Thread-pool-" + COUNTER.getAndDecrement());
        }
    }
}
