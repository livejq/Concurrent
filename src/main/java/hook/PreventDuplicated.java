package hook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 程序运行后会在项目根目录下生成.lock文件
 * 此文件内容为空，主要起到标志作用，只要检查到
 * 此文件存在，则不可能再次执行相同的程序（因为每个程序在执行前
 * 都会判断，若文件存在则直接抛出异常并终止）
 *
 * 钩子程序只有在收到终止信号时才开始执行，若主程序使用kill -9直接强制杀死，则无法收到
 * 该信号，.lock文件也就无法在程序退出前及时清理
 *
 * @author livejq
 * @since 19-8-28
 **/
public class PreventDuplicated {
    private static final String LOCK_PATH = System.getProperty("user.dir");
    private static final String LOCK_FILE = ".lock";
    // 这里为文件声明权限，后面共7个-的符号（强制规定)
    private static final String PERMISSIONS = "rw-------";

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("hook 收到结束进程信号");
            getLockFile().toFile().delete();
        }));
        checkRunning();
        for(int i = 0; i < 20; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("main 正在运行...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkRunning() throws IOException {
        Path path = getLockFile();
        if(path.toFile().exists()) {
            throw new RuntimeException("程序正在运行,无法删除lock文件");
        }
        Set<PosixFilePermission> perm = PosixFilePermissions.fromString(PERMISSIONS);
        Files.createFile(path, PosixFilePermissions.asFileAttribute(perm));
    }

    private static Path getLockFile() {
        return Paths.get(LOCK_PATH, LOCK_FILE);
    }
}
