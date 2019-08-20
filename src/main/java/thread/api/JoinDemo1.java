package thread.api;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author livejq
 * @since 2019/8/13
 */
public class JoinDemo1 {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = IntStream.range(1, 3).mapToObj(JoinDemo1::create).collect(Collectors.toList());
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "#" + i);
        }
    }

    private static Thread create(int seq) {
        return new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "#" + i);
                shortSleep();
            }
        }, String.valueOf(seq));
    }

    private static void shortSleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
