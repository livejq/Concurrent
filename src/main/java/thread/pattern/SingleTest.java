package thread.pattern;

/**
 * 测试单例的框架，采用了类加载器与反射
 *
 * <p>为了测试单便是否为真真的单例，我自己写了一个类加载器，且其父加载器设置为根加载器，
 * 这样确保Singleton由MyClassLoader加载，如果不设置为根加载器为父加载器，则默认为系统加载器，
 * 则Singleton会由系统加载器去加载，但这样我们无法卸载类加载器，如果加载Singleton的类加载器卸载不掉的话，
 * 那么第二次就不能重新加载Singleton的Class了，这样Class不能得加载则最终导致Singleton类中的静态变量重新初始化，
 * 这样就无法测试了。
 * 面测试类延迟加载的结果是可行的，同样也可用于其他单例的测试
 *
 * @author livejq
 * @since 19-8-21
 */
public class SingleTest {
  public static void main(String[] args) throws Exception {
    while (true) {
      // 不能让系统加载器直接或间接的成为父加载器
      MyClassLoader loader = new MyClassLoader(null);
      loader.setPath("home\\liveJQ\\document\\");
      CreateThread ct1 = new CreateThread(loader);
      CreateThread ct2 = new CreateThread(loader);
      ct1.start();
      ct2.start();
      ct1.join();
      ct2.join();
      if (ct1.singleton != ct2.singleton) {
        System.out.println(ct1.singleton + " " + ct2.singleton);
      }
      // System.out.println(ct1.singleton + " " + ct2.singleton);
      ct1.singleton = null;
      ct2.singleton = null;
      Thread.yield();
    }
  }
}
