package correspondence.multipart;

import java.util.LinkedList;

/**
 * notifyAll为Object类中的方法，唤醒等待队列中的所有阻塞线程进行争抢锁
 *
 * 仅改动了while和notifyAll，意为只要队列为空/满了就继续待在等待队列里面；一旦
 * 队列大于0/小于DEFAULT_MAX_EVENT就一次性唤醒所有等待的线程进行monitor锁的争抢
 *
 * @author livejq
 * @since 19-8-22
 */
class EventQueueMultiThread {
  private int max;

  static class Event {}

  private final LinkedList<Event> eventQueue = new LinkedList<>();
  private static final int DEFAULT_MAX_EVENT = 100;

  public EventQueueMultiThread() {
    this(DEFAULT_MAX_EVENT);
  }

  private EventQueueMultiThread(int max) {
    this.max = max;
  }

  public void offer(Event event) {
    synchronized (eventQueue) {
      while (eventQueue.size() >= max) {
        try {
          console("此队列已满！");
          eventQueue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      eventQueue.addLast(event);
      console("此队列最大为：" + DEFAULT_MAX_EVENT + "，此队列现在大小为：" + eventQueue.size());
      // 如果该对象没有阻塞，则忽略；被唤醒的线程需要重新获取该对象monitor所关联的lock才能继续执行
      eventQueue.notifyAll();
    }
  }

  public Event take() {
    synchronized (eventQueue) {
      while (eventQueue.isEmpty()) {
        try {
          console("此队列为空...");
          eventQueue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      Event event = this.eventQueue.removeFirst();
      console("此队列大小为：" + eventQueue.size());
      this.eventQueue.notifyAll();
      console("队列 " + event + " 正在处理中...");

      return event;
    }
  }

  private void console(String message) {
    System.out.println(String.format("%s:%s\n", Thread.currentThread().getName(), message));
  }
}
