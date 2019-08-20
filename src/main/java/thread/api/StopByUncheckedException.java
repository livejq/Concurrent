package thread.api;

/**
 * Throwable class => Error()/Exception()
 *
 * @author livejq
 * @since 19-8-19
 */
public class StopByUncheckedException {
  public static void main(String[] args) {

    try {
      new Thread(
              () -> {
                System.out.println("Sub thread is running...");
                throw new RuntimeException();
              })
          .start();
    } catch (RuntimeException e) {
      System.out.println("Sub thread throws an exception...");
    }

    System.out.println("Main thread is running...");
  }
}
