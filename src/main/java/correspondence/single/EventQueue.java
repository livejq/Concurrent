package correspondence.single;

import java.util.LinkedList;

/**
 * wait/notify为Object类中的方法，即存在于任意类中 wait有三个重载方法，wait()相当于wait(0)表示永不超时
 * wait方法必须拥有该对象的monitor，即必须在同步方法中使用 执行wait方法后会释放该对象monitor的所有权，其他线程有机会继续争抢
 *
 * 此为单线程间通信，若在多线程下则会发生数据不一致的情况（出现大于最大队列值或者小于0)
 * 为何存在synchronized同步块还会发生，造成的原因例如：假设分别有两个线程在调用offer和take方法，
 * 当队列为空的时候，两个调用take的线程会进入等待队列（问题关键：此阻塞是保留当前执行程序位置，下次被唤醒后依然继续上次位置执行）；
 * 当offer线程执行后，队列里增加了一个even并唤醒一个take线程，执行完后队列为空并同时唤醒了剩下一个等待中的线程从而导致
 * 小于0的情况（小于0是为了好理解，实际上只是对空队列进行无意义的移除操作）；满出情况发生亦是如此类似过程
 *
 * @author livejq
 * @since 19-8-22
 */
class EventQueue {
  private int max;

  static class Event {}

  private final LinkedList<Event> eventQueue = new LinkedList<>();
  private static final int DEFAULT_MAX_EVENT = 100;

  public EventQueue() {
    this(DEFAULT_MAX_EVENT);
  }

  private EventQueue(int max) {
    this.max = max;
  }

  public void offer(Event event) {
    synchronized (eventQueue) {
      if (eventQueue.size() >= max) {
        try {
          console("此队列已满！");
          eventQueue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      eventQueue.addLast(event);
      // 如果该对象没有阻塞，则忽略；被唤醒的线程需要重新获取该对象monitor所关联的lock才能继续执行
      console("此队列最大为：" + DEFAULT_MAX_EVENT + "，此队列现在大小为：" + eventQueue.size());
      eventQueue.notify();
    }
  }

  public Event take() {
    synchronized (eventQueue) {
      if (eventQueue.isEmpty()) {
        try {
          console("此队列为空...");
          eventQueue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      Event event;
      if (!eventQueue.isEmpty()) {
        event = this.eventQueue.removeFirst();
        console("此队列大小为：" + eventQueue.size());
      } else {
        console("这个只是适应单线程间通信，不能再移除了...");
        return null;
      }

      this.eventQueue.notify();
      console("队列 " + event + " 正在处理中...");

      return event;
    }
  }

  private void console(String message) {
    System.out.println(String.format("%s:%s\n", Thread.currentThread().getName(), message));
  }
}
