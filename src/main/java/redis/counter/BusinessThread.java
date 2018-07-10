package redis.counter;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.concurrent.Callable;

public class BusinessThread implements Runnable {
    private String redisKey;
    private Jedis jedis;
    public BusinessThread(String redisKey) {
        this.redisKey = redisKey;
    }

    public void run() {
        jedis = new Jedis("192.168.25.111",6379);
        //业务

        //计数
        while (true){
            try {
                Thread.sleep(1000l);
                jedis.incr(redisKey);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
