package correspondence.definelock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author livejq
 * @since 19-8-25
 */
public class BooleanLock implements Lock {
  public Thread currentThread;
  public boolean locked = false;
  private final List<Thread> blockedThread = new ArrayList<>();

  @Override
  public void lock() throws InterruptedException {
    synchronized (this) {
      // true则表明锁已被别的线程所占用，需先加入等待队列
      while (locked) {
        final Thread tempThread = Thread.currentThread();
        try {
          if (!blockedThread.contains(Thread.currentThread())) {
            blockedThread.add(currentThread);
          }
          this.wait();
          System.out.println("lock()....等待中....");
        } catch (InterruptedException e) {
          // 中断之后得移除等待队列里的此线程，避免内存泄露
          blockedThread.remove(tempThread);
          e.printStackTrace();
        }
      }
      blockedThread.remove(Thread.currentThread());
      this.locked = true;
      this.currentThread = Thread.currentThread();
    }
  }

  @Override
  public void lock(long millis) throws InterruptedException, TimeOutException {
    synchronized (this) {
      if (millis <= 0) {
        this.lock();
      } else {
        long remainingMillis = millis;
        long endMillis = System.currentTimeMillis() + remainingMillis;
        while (locked) {
          if (remainingMillis < 0) {
            throw new TimeOutException(Thread.currentThread().getName() + " 等待超时!");
          }
          if (!blockedThread.contains(Thread.currentThread())) {
            blockedThread.add(Thread.currentThread());
          }
          System.out.println(
              Thread.currentThread().getName() + " 等待中....还剩 " + remainingMillis + " 毫秒");
          // 关键，注意先后顺序
          this.wait(remainingMillis);
          remainingMillis = endMillis - System.currentTimeMillis();
        }
        blockedThread.remove(Thread.currentThread());
        this.locked = true;
        this.currentThread = Thread.currentThread();
      }
    }
  }

  @Override
  public void unlock() {
    synchronized (this) {
      // 哪个线程加的锁就由哪个线程来解锁
      if (currentThread == Thread.currentThread()) {
        this.locked = false;
        Optional.of(currentThread.getName() + " 正在释放锁").ifPresent(System.out::println);
        this.notifyAll();
      }
    }
  }

  @Override
  public List<Thread> getBlockedThread() {
    return blockedThread;
  }
}
