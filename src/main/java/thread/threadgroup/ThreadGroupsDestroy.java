package thread.threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 19-8-28
 **/
public class ThreadGroupsDestroy {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group2 = new ThreadGroup("group2");
        // 若下面未注释掉，则group2存在active线程，销毁时会抛出IllegalThreadStateException异常
        /*new Thread(group2, () -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();*/
        System.out.println("group2是否已销毁:" + group2.isDestroyed());
        ThreadGroup mainGroup = group2.getParent();
        mainGroup.list();

        group2.destroy();
        System.out.println("group2是否已销毁:" + group2.isDestroyed());
        mainGroup.list();
    }
}
