package thread.pattern;

/**
 * 使用内部类实现延迟加载
 * 为了做到真真的延迟加载，双重检测在 Java 中是行不通的，所以只能借助于另一类的类加载加延迟加载
 * @author livejq
 * @since 19-8-21
 */
public class SingletonDemoFinal {

  private SingletonDemoFinal() {}

  public static class Holder {
    static SingletonDemoFinal instance = new SingletonDemoFinal();
  }

  public static SingletonDemoFinal getInstance() {
    return Holder.instance;
  }
}
