package thread.sync;

/**
 * CPU时间片轮询调度执行产生的问题：
 *
 * <p>1.某个号码被多次显示 2.某个号码被略过没有显示 3.号码超过最大值
 *
 * <p>问题：monitor enter之后获得monitor所有权的线程除了计数器加1外还有其他标识其获得该锁的凭证吗？重入加1是咋回事？ monitor exit
 * 后其它被blocked的线程在请求获得该锁时咋不会发生争抢，是synchronized串行化中存在等待队列吗？
 *
 * <p>知识点：synchronized最好不要放在run上面，作用域太大反而失去了并发的优势，效率降低；应该只作用于共享资源（数据）
 * 线程之间进行monitor lock的争抢只能发生在与monitor关联的同一个引用上
 *
 * @author livejq
 * @since 19-8-20
 */
public class TicketWindow implements Runnable {

  private static final int MAX = 500;
  private int index = 1;
  private static final Object MUTEX = new Object();

  @Override
  public void run() {
    synchronized (MUTEX) {
      while (index <= MAX) {
        System.out.println(Thread.currentThread() + " 的号码为" + (index++));
      }
    }
  }

  public static void main(String[] args) {
    TicketWindow ticket = new TicketWindow();
    Thread t1 = new Thread(ticket, "一号窗口");
    Thread t2 = new Thread(ticket, "二号窗口");
    Thread t3 = new Thread(ticket, "三号窗口");
    Thread t4 = new Thread(ticket, "四号窗口");
    Thread t5 = new Thread(ticket, "五号窗口");
    Thread t6 = new Thread(ticket, "六号窗口");
    Thread t7 = new Thread(ticket, "七号窗口");
    t1.start();
    t2.start();
    t3.start();
    t4.start();
    t5.start();
    t6.start();
    t7.start();
  }
}
