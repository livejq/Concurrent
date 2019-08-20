package thread.api;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 2019/8/14
 */
public class InterruptDemo2 {
  public static void main(String[] args) throws InterruptedException {

    Thread t2 =
        new Thread(
            () -> {
              while (true) {
                System.out.println(Thread.interrupted());
              }
            },
            "t2");
    t2.setDaemon(true);
    t2.start();
    TimeUnit.MILLISECONDS.sleep(5);
    System.out.println("准备中断...");
    t2.interrupt();
    Thread.yield();
    System.out.println("已中断");
  }
}
