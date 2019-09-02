package threadPool;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 2019/9/1
 */
public class ThreadPoolTest {
    public static void main(String args[]) throws InterruptedException {
        final ThreadPool threadPool = new BasicThreadPool(4,6,10,1000);
        for(int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName() + " 正在运行...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(5);
        for(;;) {
            if(!threadPool.isShutdown()) {
                System.out.println("活跃线程总数：" + threadPool.getActiveSize());
                System.out.println("初始线程总数：" + threadPool.getInitSize());
                System.out.println("核心线程总数：" + threadPool.getCoreSize());
                System.out.println("最大线程总数：" + threadPool.getMaxSize());
                System.out.println("任务队列总数：" + threadPool.getQueueSize());
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                TimeUnit.SECONDS.sleep(2);
//                threadPool.shutdown();
            }
        }

    }
}
