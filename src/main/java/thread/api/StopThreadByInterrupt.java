package thread.api;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 19-8-17
 */
public class StopThreadByInterrupt {
  public static void main(String[] args) throws InterruptedException {
      // 方式一：
    /*Thread t1 =
        new Thread(
            () -> {
              while (!Thread.currentThread().isInterrupted()) {
                System.out.println("我在工作...");
              }
              System.out.println("====================休息中...");
            });
    t1.start();
    TimeUnit.SECONDS.sleep(2);
    System.out.println("即将终止 t1 线程");
    t1.interrupt();*/

      // 方式二：
    Thread t1 =
        new Thread(
            () -> {
              for (; ; ) {
                try {
                  System.out.println("我在工作...");
                  TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                  break;
                }
              }
              System.out.println("====================休息中...");
            });
    t1.start();
    TimeUnit.SECONDS.sleep(1);
    t1.interrupt();
  }
}
