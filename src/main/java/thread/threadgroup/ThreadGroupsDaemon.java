package thread.threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 19-8-28
 **/
public class ThreadGroupsDaemon {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group1 = new ThreadGroup("group1");
        ThreadGroup group2 = new ThreadGroup("group2");
        new Thread(group1, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        Thread t2 = new Thread(group2, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2");
        // 设置了守护进程后，线程组group2在main退出时自动销毁（前提是其不存在active子线程）
        group2.setDaemon(true);
        t2.start();

        TimeUnit.SECONDS.sleep(4);
        System.out.println("group1是否已销毁:" + group1.isDestroyed());
        System.out.println("group2是否已销毁:" + group2.isDestroyed());
    }
}
