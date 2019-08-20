package thread.sync;

import java.util.concurrent.TimeUnit;

/**
 * 证实：使用synchronized关键字的同步类中的不同实例方法，争抢的是同一个monitor的lock
 *
 * @author livejq
 * @since 19-8-21
 */
public class ThisMonitor {
  synchronized void method1() {
    System.out.println(Thread.currentThread().getName() + " 开始执行 method1 方法");
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      System.out.println("呀！打断了");
    }
  }

  /*synchronized void method2() {
    System.out.println(Thread.currentThread().getName() + " 开始执行 method2 方法");
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      System.out.println("呀！打断了");
    }
  }*/

  void method2() {
    synchronized (this) {
      System.out.println(Thread.currentThread().getName() + " 开始执行 method2 方法");
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        System.out.println("呀！打断了");
      }
    }
  }

  public static void main(String[] args) {
    ThisMonitor thisMonitor = new ThisMonitor();
    new Thread(thisMonitor::method1, "T1").start();
    new Thread(thisMonitor::method2, "T2").start();
  }
}
