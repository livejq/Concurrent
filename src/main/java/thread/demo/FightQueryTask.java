package thread.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author livejq
 * @date 19-8-17
 **/
public class FightQueryTask extends Thread implements FightQuery {
    private String origin;
    private String destination;

    private List<String> fightList = new ArrayList<>();

    public FightQueryTask(String airLine, String origin, String destination) {
        super("[" + airLine + "]");
        this.destination = destination;
        this.origin = origin;
    }

    @Override
    public void run() {
        System.out.println(String.format("%s-正在查询从 %s 到 %s 的航班", getName(), origin, destination));
        int randomVal = ThreadLocalRandom.current().nextInt(10);
        try {
            TimeUnit.SECONDS.sleep(randomVal);
            this.fightList.add(getName() + "-" + randomVal);
            System.out.println(String.format("航班 %s 查询成功！", getName()));
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<String> get() {
        return fightList;
    }
}
