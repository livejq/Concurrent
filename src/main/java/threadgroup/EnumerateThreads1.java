package threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 19-8-28
 **/
public class EnumerateThreads1 {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group1 = new ThreadGroup("group1");
        group1.setDaemon(true);
        Thread thread = new Thread(group1, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1");
        thread.setDaemon(true);
        thread.start();

        TimeUnit.SECONDS.sleep(3);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        Thread[] list = new Thread[mainGroup.activeCount()];
        System.out.println("list.length:" + list.length);
        for (Thread temp : list) {
            if(temp != null) {
                System.out.println(temp + " is not null");
                System.out.println(temp.getThreadGroup());
            }
        }
        int recurseSize = mainGroup.enumerate(list);
        System.out.println(recurseSize);
        recurseSize = mainGroup.enumerate(list, false);
        System.out.println(recurseSize);
    }
}
