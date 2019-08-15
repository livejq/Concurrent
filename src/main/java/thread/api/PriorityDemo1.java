package thread.api;

/**
 * 高优先级的线程比低优先级的线程有更高的几率得到执行，
 * 实际上这和操作系统及虚拟机版本相关，有可能即使设置了线程的优先级也不会产生任何作用
 *
 * @author livejq
 * @since 2019/8/13
 */
public class PriorityDemo1 extends Thread {

    public PriorityDemo1(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            System.out.println("优先级：" + Thread.currentThread().getPriority());
        }
    }

    public static void main(String[] args) {
        // main线程创建的子线程优先级若没有定义，则默认和父线程一样的优先级（main优先级默认为5）
        // 创建子线程时，线程组未定义的同样默认加入到父线程所属的父线程组中（这里是main线程组）。
        Thread t1 = new PriorityDemo1("A");
        Thread t2 = new PriorityDemo1("B");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
    }
}
