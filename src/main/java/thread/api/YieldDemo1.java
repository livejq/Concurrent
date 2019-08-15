package thread.api;

import java.util.stream.IntStream;

/**
 * 提醒CPU调度器，“我”愿意放弃当前CPU资源，若CPU资源不紧张则一般会忽略这个提醒
 * yield只是个（hint），CPU调度器并不会每次都能满足yield的需要
 *
 *
 * @author livejq
 * @since 2019/8/14
 */
public class YieldDemo1 {
    public static void main(String[] args) {

        IntStream.range(0,2).mapToObj(YieldDemo1::create).forEach(Thread::start);

    }

    private static Thread create(int index) {
        return new Thread(() -> {
            if(index == 0) {
                Thread.yield();
            }
            System.out.println(index);
        });
    }
}
