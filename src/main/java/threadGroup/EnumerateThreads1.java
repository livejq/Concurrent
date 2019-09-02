package threadGroup;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 19-8-28
 **/
public class EnumerateThreads1 {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group1 = new ThreadGroup("group1");
        ThreadGroup group2 = new ThreadGroup(group1, "group2");
        group1.setDaemon(true);
        Thread thread = new Thread(group2, () -> {
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

        // 注意：不要漏掉！！！！！其中的所有Group和Thread都属于main主线程组中的====================
        TimeUnit.SECONDS.sleep(3);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        // 返回的是当前所在线程组（main组）中的active线程（递归后）以及把当前线程组当做父线程组的子线程组
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
