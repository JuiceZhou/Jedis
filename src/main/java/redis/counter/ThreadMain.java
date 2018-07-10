package redis.counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主函数
 */
public class ThreadMain {
    public static void main(String[] args) {
        //创建线程池，其中三个线程用来做业务，一个线程用来做计数器
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(new BusinessThread("business"));
        executorService.submit(new BusinessThread("business"));
        executorService.submit(new BusinessThread("business"));
        executorService.submit(new Counter("business"));
    }
}
