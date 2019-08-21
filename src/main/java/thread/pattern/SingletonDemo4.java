package thread.pattern;

/**
 * 双重检测延迟加载单例模式
 *
 * 借助于 ThreadLocal，将临界资源（需要同步的资源）线程局部化，
 * 具体到本例就是将双重检测的第一层检测条件 if (instance == null) 转换为了线程局部范围内来作。
 * 这里的 ThreadLocal 也只是用作标识而已，用来标示每个线程是否已访问过，如果访问过，
 * 则不再需要走同步块，这样就提高了一定的效率。但是 ThreadLocal 在 jdk1.4 以前的版本都较慢，
 * 但这与 volatile 相比却是安全的。
 *
 * @author livejq
 * @since 19-8-21
 */
public class SingletonDemo4 {

  private static SingletonDemo4 singletonDemo1;
  /**
   * 自定义的ThreadLocal应至少调用一次remove（），尤其是在线程池的时候，
   * 线程会被复用，如果不清理的话会影响后续业务逻辑和造成内存泄露，尽量在
   * 代码块try-final进行回收
   */
  private static ThreadLocal<SingletonDemo4> perThreadInstance = new ThreadLocal<>();

  private SingletonDemo4() {}

  public static SingletonDemo4 getInstance() {

    if (perThreadInstance.get() == null) {
        createInstance();
    }

    return singletonDemo1;
  }

  private static void createInstance() {
    synchronized (SingletonDemo4.class) {
      if (singletonDemo1 == null) {
        singletonDemo1 = new SingletonDemo4();
      }
    }

    perThreadInstance.set(singletonDemo1);
  }

  public static void remove() {
      perThreadInstance.remove();
  }
}
