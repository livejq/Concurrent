package correspondence.definelock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class BooleanLockTest {
    private BooleanLock booleanLock = new BooleanLock();
    @Test
    public void testLock_interrupt() {
        Thread x1 =
                new Thread(
                        () -> {
                            try {
                                booleanLock.lock();
                                if (Thread.currentThread() == booleanLock.currentThread) {
                                    System.out.println(Thread.currentThread().getName() + " 成功获得锁");
                                }
                            } catch (InterruptedException e) {
                                System.out.println("哎呀～" + Thread.currentThread().getName() + "中断了");
                            }
                        },
                        "x1");
        x1.setPriority(10);
        x1.start();
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("x1 优先级：" + x1.getPriority());
        } catch (InterruptedException e) {
            e.printStackTrace();
    }
        Thread t1 =
                new Thread(
                        () -> {
                            try {
                                booleanLock.lock();
                                if (Thread.currentThread() == booleanLock.currentThread) {
                                    System.out.println(Thread.currentThread().getName() + " 成功获得锁");
                                }
                            } catch (InterruptedException e) {
                                System.out.println("哎呀～" + Thread.currentThread().getName() + "中断了");
                            }finally{
                                // 包含在try-finally中确保线程获得锁后执行释放操作
                                booleanLock.unlock();
                                if (!booleanLock.locked && booleanLock.currentThread == Thread.currentThread()) {
                                    System.out.println(Thread.currentThread().getName() + " 成功释放锁");
                                }
                            }
                        },
                        "t1");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 前面必须要有线程占用了锁，才能使此线程阻塞，然后才能中断
        t1.interrupt();
    }

  @Test
  public void testLock_useful() {
    BooleanLock booleanLock = new BooleanLock();
      Thread t1 =
              new Thread(
                      () -> {
                          try {
                              booleanLock.lock();
                              if (Thread.currentThread() == booleanLock.currentThread) {
                                  System.out.println(Thread.currentThread().getName() + " 成功获得锁");
                              }
                              TimeUnit.SECONDS.sleep(10);
                          } catch (InterruptedException e) {
                              System.out.println("哎呀～" + Thread.currentThread().getName() + "中断了");
                          }finally{
                              // 包含在try-finally中确保线程获得锁后执行释放操作
                              booleanLock.unlock();
                              if (!booleanLock.locked && booleanLock.currentThread == Thread.currentThread()) {
                                  System.out.println(Thread.currentThread().getName() + " 成功释放锁");
                              }
                          }
                      },
                      "t1");
      t1.start();
      try {
          TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      Thread t2 =
              new Thread(
                      () -> {
                          try {
                              booleanLock.lock(1000);
                              if (Thread.currentThread() == booleanLock.currentThread) {
                                  System.out.println(Thread.currentThread().getName() + " 在规定时间内成功获得锁");
                              }
                          } catch (InterruptedException e) {
                              System.out.println("哎呀～" + Thread.currentThread().getName() + "中断了");
                          } catch (TimeOutException t) {
                              System.out.println(t.getMessage());
                          } finally {
                              booleanLock.unlock();
                              if (!booleanLock.locked && booleanLock.currentThread == Thread.currentThread()) {
                                  System.out.println(Thread.currentThread().getName() + " 成功释放锁");
                              }
                          }
                      },
                      "t2");
      t2.start();

      Thread t3 =
              new Thread(
                      () -> {
                          try {
                              booleanLock.lock(1000);
                              if (Thread.currentThread() == booleanLock.currentThread) {
                                  System.out.println(Thread.currentThread().getName() + " 在规定时间内成功获得锁");
                              }
                          } catch (InterruptedException e) {
                              System.out.println("哎呀～" + Thread.currentThread().getName() + "中断了");
                          } catch (TimeOutException t) {
                              System.out.println(t.getMessage());
                          } finally {
                              booleanLock.unlock();
                              if (!booleanLock.locked && booleanLock.currentThread == Thread.currentThread()) {
                                  System.out.println(Thread.currentThread().getName() + " 成功释放锁");
                              }
                          }
                      },
                      "t3");
      t3.start();
      try {
          TimeUnit.SECONDS.sleep(10);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
  }
}
