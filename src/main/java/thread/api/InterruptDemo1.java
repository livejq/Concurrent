package thread.api;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 2019/8/14
 */
public class InterruptDemo1 {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "正在运行...");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println(String.format("oh, 我被打断了吗 ？ %s", Thread.currentThread().isInterrupted()));
            }
        }, "t1");
        t1.start();
        System.out.println(t1.getName() + "是否被打断 " + t1.isInterrupted());
        // 若调用线程的中断方法时，该线程并未处于阻塞状态，同样生效，即该线程的中断状态（false或true）一样会改变
        t1.interrupt();
        System.out.println(t1.getName() + "是否被打断 " + t1.isInterrupted());
        TimeUnit.SECONDS.sleep(2);
        // 不会自动擦除中断状态true，直到再次调用可中断方法
        for (int i = 0; i < 10; i++) {
            System.out.println(t1.getName() + "是否被打断 " + t1.isInterrupted() + i);
        }
    }
}
