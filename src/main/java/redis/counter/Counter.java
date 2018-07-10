package redis.counter;

import redis.clients.jedis.Jedis;

import java.util.concurrent.Callable;

public class Counter implements Runnable {
    private String redisKey;
    private Jedis jedis;

    public Counter(String redisKey) {
        this.redisKey = redisKey;
    }

    public void run() {
        jedis = new Jedis("192.168.25.111",6379);
        while (true){
            try {
                Thread.sleep(1000l);
                String count = jedis.get(redisKey);
                System.out.println("目前总数为："+count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
