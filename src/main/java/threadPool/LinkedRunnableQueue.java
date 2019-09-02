package threadPool;

import java.util.LinkedList;

/**
 * @author livejq
 * @since 2019/9/1
 */
public class LinkedRunnableQueue implements RunnableQueue {

    private int limit;
    private DenyPolicy denyPolicy;
    private final LinkedList<Runnable> linkedList = new LinkedList<>();
    private ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (linkedList) {
            if (linkedList.size() > limit) {
                denyPolicy.reject(runnable, threadPool);
            } else {
                linkedList.addLast(runnable);
                linkedList.notifyAll();
            }
        }

    }

    @Override
    public Runnable take() {
        synchronized(linkedList) {
            if(linkedList.isEmpty()) {
                try {
                    linkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return linkedList.removeFirst();
        }
    }

    @Override
    public int size() {
        synchronized (linkedList) {
            return linkedList.size();
        }
    }
}
