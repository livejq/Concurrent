package thread.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author livejq
 * @since 19-8-17
 */
public class FightQueryExample {
  private static List<String> fightCompany = Arrays.asList("航空1号", "航空2号", "航空3号");

  public static void main(String[] args) {
    List<String> results = search("广州", "上海");
    results.forEach(System.out::println);
  }

  private static List<String> search(String origin, String destination) {
    final List<String> result = new ArrayList<>();
    // 根据客户输入的线路，从每个航空公司中查找相关航班的情况
    List<FightQueryTask> tasks =
        fightCompany.stream()
            .map(f -> createFightQueryTask(f, origin, destination))
            .collect(toList());
    tasks.forEach(Thread::start);
    // 阻塞当前线程，优先收集最后一个线程中返回的信息，然后汇总
    tasks.forEach(
        f -> {
          try {
            f.join();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        });

    // 将每个线程返回的List<String>遍历出一个个String,然后全部都加入到新的result集合中作为返回值
    tasks.stream().map(FightQuery::get).forEach(result::addAll);

    return result;
  }

  private static FightQueryTask createFightQueryTask(
      String fight, String origin, String destination) {
    return new FightQueryTask(fight, origin, destination);
  }
}
