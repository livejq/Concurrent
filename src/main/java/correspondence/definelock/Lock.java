package correspondence.definelock;

import java.util.List;

/**
 * @author livejq
 * @since 19-8-25
 **/
public interface Lock {
    /** 除非获取到锁，否则永远阻塞，相比与synchronized是可中断的 */
    void lock() throws InterruptedException;

    /** 在lock（）基础上增加了超时功能 */
    void lock(long millis) throws InterruptedException, TimeOutException;

    /** 释放锁 */
    void unlock();

    /** 获取阻塞队列中的线程 */
    List<Thread> getBlockedThread();
}
