package thread.api;

import java.util.concurrent.TimeUnit;

/**
 * interrupted()的中断方法与isInterrupted()的不同之处（源码）：
 * 前者为：public static boolean interrupted() { return currentThread.isInterrupted(true);}
 * 后者为：public static boolean isInterrupted() { return isInterrupted(false);}
 *
 * @author livejq
 * @since 2019/8/14
 */
public class InterruptDemo3 {
    public static void main(String[] args) {
        // 获取线程的类加载器。也存在设置方法，可以打破java类加载器的父委托机制，被称为java类加载器的后门
        System.out.println(Thread.currentThread().getContextClassLoader() + ",id:" + Thread.currentThread().getId());
        System.out.println(Thread.currentThread().getName() + "#" + Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + "@" + Thread.currentThread().isInterrupted());
        try {
            // 不论设置阻塞多久，都会立即中断
            TimeUnit.MINUTES.sleep(3);
        } catch (InterruptedException e) {
            System.out.println("哎呀~中断了");
        }
    }
}
