package threadPool;

/**
 * @author livejq
 * @since 2019/9/1
 */
public class InternalTask implements Runnable {
    private RunnableQueue runnableQueue;
    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        // 此保证了在线程回收时不至于将Runnable的线程中还未完成的任务线程中断掉
        while(running && !Thread.currentThread().isInterrupted()) {
            Runnable task = runnableQueue.take();
            task.run();
        }
    }

    public void stop() {
        this.running = false;
    }
}
