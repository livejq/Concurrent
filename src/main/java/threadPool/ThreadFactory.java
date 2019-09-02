package threadPool;

/**
 * @author livejq
 * @since 2019/9/1
 */
public interface ThreadFactory {
    Thread createThread(Runnable runnable);
}
