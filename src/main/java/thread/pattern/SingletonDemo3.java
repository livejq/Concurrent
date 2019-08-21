package thread.pattern;

/**
 * 双重检测延迟加载单例模式
 *
 * 相关文章链接地址：https://www.iteye.com/topic/652440 Jdk
 * 1.2之后，JMM发生了根本的改变，分配空间、初始化、调用构造方法只会在线程的工作存储区中完成，
 * 在没有向主存储区复制赋值时，其他线程绝对不可能见到这个过程，而这个字段复制到主存区的过程,
 * 更不会有分配空间后 没有初始化或没有调用构造方法的可能。 在 JAVA 中, 一切都是按引用的值复制的，
 * 向主存储区同步其实就是把线程工作存储区的这个已经构造好的对象从压缩堆地址值 COPY
 * 给主存储区的那个变量。这个过程对于其它线程, 要么是 resource 为 null, 要么是完整的对象，
 * 绝对不会把一个已经分配空间却没有构造好的对象让其它线程可见
 *
 * @author livejq
 * @since 19-8-21
 */
public class SingletonDemo3 {

  private static SingletonDemo3 singletonDemo1 = null;

  private SingletonDemo3() {}

  public static SingletonDemo3 getInstance() {

    if (singletonDemo1 == null) {
      synchronized (SingletonDemo3.class) {
        if (singletonDemo1 == null) {
          singletonDemo1 = new SingletonDemo3();
        }
      }
    }

    return singletonDemo1;
  }
}
