package hook;

import java.util.concurrent.TimeUnit;

/**
 * 从相关源码分析可知：若当前线程设置了默认的异常回调接口，
 * 则发生异常时默认抛出此定义的异常信息，否则从其所属线程组中判断是
 * 否设置了默认异常回调接口，若是则调用，否则类似的继续查找，知道main组之上的
 * System Group（全局默认异常回调接口）完了之后，
 * 若还为空，则直接将异常的堆栈信息定向到System.err中输出
 *
 * @author livejq
 * @since 19-8-28
 **/
public class CaptureThreadException {
    public static void main(String[] args) {
        // 设置回调接口（与不设置进行对比）
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getName() + " 发生异常!");
            e.printStackTrace();
        });

        final Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1/0);
        }, "t1");
        thread.start();
    }
}
