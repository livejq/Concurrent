package correspondence.multipart;

import java.util.concurrent.TimeUnit;

/**
 * 注意不要用错了EventQueue(emmmm)
 *
 * @author livejq
 * @since 19-8-22
 **/
class EventClientMultiThread {
  public static void main(String[] args) {
    final EventQueueMultiThread eventQueue = new EventQueueMultiThread();
    new Thread(() -> {
        for(;;) {
            eventQueue.offer(new EventQueueMultiThread.Event());
        }
    }, "Producer1").start();

    new Thread(() -> {
        for(;;) {
            eventQueue.offer(new EventQueueMultiThread.Event());
        }
    }, "Producer2").start();

      new Thread(() -> {
          for(int i = 0; i < 50; i++) {
              eventQueue.take();
              try {
                  TimeUnit.MILLISECONDS.sleep(10);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }, "Consumer1").start();

      new Thread(() -> {
          for(int i = 0; i < 500; i++) {
              eventQueue.take();
              try {
                  TimeUnit.MILLISECONDS.sleep(10);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }, "Consumer2").start();
  }
}
