package threadPool;

/**
 * @author livejq
 * @since 2019/9/1
 */
public interface ThreadPool {
    void execute(Runnable runnable);
    void shutdown();
    int getInitSize();
    int getMaxSize();
    int getCoreSize();
    int getQueueSize();
    int getActiveSize();
    boolean isIllegalParam();
    boolean isShutdown();
}
