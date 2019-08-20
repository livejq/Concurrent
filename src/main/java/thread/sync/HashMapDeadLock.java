package thread.sync;

import java.util.HashMap;

/**
 * @author livejq
 * @since 19-8-21
 */
public class HashMapDeadLock {
  private final HashMap<String, String> map = new HashMap<>();

  void add(String key, String value) {
    this.map.put(key, value);
  }

  public static void main(String[] args) {
    HashMapDeadLock hashMapDeadLock = new HashMapDeadLock();
    for (int i = 0; i < 2; i++) {
      new Thread(
              () -> {
                for (int j = 1; j < 100000; j++) {
                  hashMapDeadLock.add(String.valueOf(j), String.valueOf(j));
                }
              })
          .start();
    }
    System.out.println(Integer.MAX_VALUE);
  }
}
