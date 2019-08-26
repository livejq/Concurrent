package thread.api;

import java.util.concurrent.TimeUnit;

/**
 * 验证wait()被打断后，其中断信号被捕捉后自动擦除interrupt标识
 * 用哪个对象的monitor进行同步，就只能用哪个对象的wait()/notify()方法进行操作
 *
 * @author livejq
 * @since 19-8-22
 */
public class WaitDemo1 {
  public static void main(String[] args) throws InterruptedException {
    Thread t1 =
        new Thread(
            new Runnable() {
              @Override
              public synchronized void run() {
                try {
                  System.out.println("wait()被调用:" + Thread.currentThread().isInterrupted());
                  this.wait();
                } catch (InterruptedException e) {
                  System.out.println("前：" + Thread.currentThread().isInterrupted());
                  System.out.println("呀～被打断了");
                  System.out.println("后：" + Thread.currentThread().isInterrupted());
                }
              }
            });
    t1.start();
    TimeUnit.SECONDS.sleep(2);
    System.out.println("准备打断wait()方法...");
    t1.interrupt();
    System.out.println("验证是否擦除interrupt标识：" + t1.isInterrupted());
  }
}
