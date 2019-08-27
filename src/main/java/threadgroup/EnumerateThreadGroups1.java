package threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @since 19-8-28
 **/
public class EnumerateThreadGroups1 {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group2 = new ThreadGroup("group2");
        ThreadGroup group3 = new ThreadGroup(group2, "group3");
        group2.setDaemon(true);
        Thread thread2 = new Thread(group2, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2");
        thread2.setDaemon(true);
        thread2.start();

        TimeUnit.SECONDS.sleep(3);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup[] list = new ThreadGroup[mainGroup.activeGroupCount()];
        System.out.println("list.length:" + list.length);
        for (ThreadGroup temp : list) {
            if(temp != null) {
                System.out.println(temp + " is not null");
                temp.list();
                System.out.println(temp.getName());
            }
        }
        mainGroup.list();
        int recurseSize = mainGroup.enumerate(list);
        System.out.println(recurseSize);
        recurseSize = mainGroup.enumerate(list, false);
        System.out.println(recurseSize);

        System.out.println("group2.getMaxPriority(): " + group2.getMaxPriority());
        System.out.println("thread2.getPriority(): " + thread2.getPriority());
        group2.setMaxPriority(3);
        // 虽然说当前的线程优先级不能大于所在组的优先级，根据下面的输出可知，
        // 这实际上是指设置完优先级后所创建的线程或线程组不可能大于刚才设定的优先级
        System.out.println("group2.getMaxPriority(): " + group2.getMaxPriority());
        System.out.println("thread2.getPriority(): " + thread2.getPriority());
    }
}
