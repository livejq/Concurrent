package thread.sync;

/**
 * 交叉死锁发生情景
 *
 * @author livejq
 * @since 19-8-21
 */
public class CrossLock {
  private static final Object MUTEX_READ = new Object();
  private static final Object MUTEX_WRITE = new Object();

  void read() {
    synchronized (MUTEX_READ) {
      System.out.println("read() 正在读取...");
      synchronized (MUTEX_WRITE) {
        System.out.println("read() 正在写入...");
      }
        System.out.println("read() 退出写入");
    }
      System.out.println("read() 退出读取");
  }

  void write() {
    synchronized (MUTEX_WRITE) {
      System.out.println("write() 正在写入...");
      synchronized (MUTEX_READ) {
        System.out.println("write() 正在读取...");
      }
        System.out.println("write() 退出读取");
    }
      System.out.println("write() 退出写入");
  }

  public static void main(String[] args) {
      CrossLock crossLock = new CrossLock();
      new Thread(() -> {
          while (true) {
              crossLock.read();
          }
      }).start();
      new Thread(() -> {
          while (true) {
              crossLock.write();
          }
      }).start();
  }
}
