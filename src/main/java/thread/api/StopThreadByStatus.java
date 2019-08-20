package thread.api;

import java.util.concurrent.TimeUnit;

/**
 * 设置设置中断标识符并使用 && ，防止逻辑中没有调用中断方法或因中断信号被擦除而无法停止
 *
 * @author livejq
 * @since 19-8-19
 */
public class StopThreadByStatus {
  private static class MyTask extends Thread {
    private volatile boolean closed = false;

    @Override
    public void run() {
      System.out.println("开始工作");
      while (!closed && !isInterrupted()) {
        System.out.println("正在工作中...");
      }
      System.out.println("工作结束");
    }

    private void close() {
      this.closed = true;
      this.interrupt();
    }
  }

  public static void main(String[] args) {
    MyTask task = new MyTask();
    task.start();
    try {
      TimeUnit.MILLISECONDS.sleep(1);
      System.out.println("准备结束");
      task.close();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
