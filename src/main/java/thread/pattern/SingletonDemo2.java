package thread.pattern;

/**
 * 从速度和反应时间来讲，“饿汉式”好； 从资源利用效率上来说，“懒汉式”好。
 *
 * <p>延迟加载单例模式，“懒汉式”
 *
 * @author livejq
 * @since 19-8-21
 */
public class SingletonDemo2 {

  private static SingletonDemo2 singletonDemo1 = null;

  private SingletonDemo2() {}

  public static synchronized SingletonDemo2 getInstance() {

    if (singletonDemo1 == null) {
      singletonDemo1 = new SingletonDemo2();
    }
    return singletonDemo1;
  }
}
