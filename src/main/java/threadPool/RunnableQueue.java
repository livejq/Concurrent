package threadPool;

/**
 * @author livejq
 * @since 2019/9/1
 */
public interface RunnableQueue {
    void offer(Runnable runnable);
    Runnable take();
    int size();
}
