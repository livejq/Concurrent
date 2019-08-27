package hook;

import java.util.concurrent.TimeUnit;

/**
 * 钩子程序可在程序终止前做些零碎的事情，如：关闭数据库连接、
 * socket链接、文件句柄等资源的释放；但需要注意最好不要做耗时非常长的
 * 事情，不然导致主程序迟迟无法退出
 *
 * @author livejq
 * @since 19-8-28
 **/
public class ThreadHook {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("钩子 1 线程已启动...");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("钩子 1 线程即将退出");
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("钩子 2 线程已启动...");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("钩子 2 线程即将退出");
            }
        });

        System.out.println("main 程序终止！钩子线程准备开启");
    }
}
