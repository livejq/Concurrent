package correspondence.single;

import java.util.concurrent.TimeUnit;

/**
 * 在多线程的情况下执行单线程间通信的
 * 这个EventQueue会发生大于最大队列值或者小于0的
 * 情况
 *
 * @author livejq
 * @since 19-8-22
 **/
public class EventClient {
    public static void main(String[] args) {
        final EventQueue eventQueue = new EventQueue();
        new Thread(() -> {
            for(;;) {
                eventQueue.offer(new EventQueue.Event());
            }
        }, "Producer1").start();

        new Thread(() -> {
            for(;;) {
                eventQueue.offer(new EventQueue.Event());
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
            for(int i = 0; i < 50; i++) {
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
