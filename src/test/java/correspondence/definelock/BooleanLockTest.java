package correspondence.definelock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class BooleanLockTest {
  @Test
  public void testLock_useful() {
    BooleanLock booleanLock = new BooleanLock();
    synchronized (this) {
      try {
        booleanLock.lock();
        System.out.println("主线程获得booleanLock锁");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    Thread t1 =
        new Thread(
            () -> {
              try {
                booleanLock.lock();
                if (Thread.currentThread() == booleanLock.currentThread) {
                  System.out.println("t1 成功获得锁");
                  booleanLock.unlock();
                  if (!booleanLock.locked) {
                    System.out.println("t1 成功释放锁");
                  }
                }
              } catch (InterruptedException e) {
                System.out.println("哎呀～中断了");
              }
            },
            "t1");
    t1.start();
    Thread t2 =
        new Thread(
            () -> {
              try {
                TimeUnit.SECONDS.sleep(5);
                booleanLock.lock(10000);
                if (Thread.currentThread() == booleanLock.currentThread) {
                  System.out.println("t2 在规定时间内成功获得锁");
                  booleanLock.unlock();
                  if (!booleanLock.locked) {
                    System.out.println("t2 成功释放锁");
                  }
                }
              } catch (InterruptedException e) {
                System.out.println("哎呀～中断了");
              }
            },
            "t2");
    t2.start();

    System.out.println("即将释放booleanLock...");
    booleanLock.unlock();
    try {
      TimeUnit.SECONDS.sleep(20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
