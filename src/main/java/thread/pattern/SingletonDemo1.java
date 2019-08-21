package thread.pattern;

/**
 * 从速度和反应时间来讲，“饿汉式”好； 从资源利用效率上来说，“懒汉式”好。
 *
 * <p>非延迟加载单例模式，“饿汉式”
 *
 * @author livejq
 * @since 19-8-21
 */
public class SingletonDemo1 {

  private static SingletonDemo1 singletonDemo1 = new SingletonDemo1();
  private SingletonDemo1() {}

  public static SingletonDemo1 getInstance() {
    return singletonDemo1;
  }
}
