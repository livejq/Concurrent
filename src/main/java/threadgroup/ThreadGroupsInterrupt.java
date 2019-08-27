package threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 19-8-28
 **/
public class ThreadGroupsInterrupt {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group1 = new ThreadGroup("group1");
        ThreadGroup group2 = new ThreadGroup(group1, "group2");
        group1.setDaemon(true);
        group2.setDaemon(true);

        Thread thread1 = new Thread(group1, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + " 已退出！");
        }, "thread1");
        thread1.setDaemon(true);
        thread1.start();

        Thread thread2 = new Thread(group2, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + " 已退出！");
        }, "thread2");
        thread2.setDaemon(true);
        thread2.start();

        TimeUnit.SECONDS.sleep(3);
        group1.list();
        group1.interrupt();
    }
}
