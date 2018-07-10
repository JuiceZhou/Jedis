package redis.String;

import redis.clients.jedis.Jedis;

import java.util.List;

public class StringAPITest {
    public static void main(String[] args) {
        //构建jedis客户端
        Jedis jedis = new Jedis("192.168.25.111",6379);
        //操作一个key-string:存入、获取
        jedis.set("AKey","AValue");
        System.out.println(jedis.get("AKey"));
        //一次操作多个key-string
        jedis.mset("BKey","BValue","CKey","CValue","DKey","DValue");
        List<String> listString = jedis.mget("BKey", "CKey", "DKey");
        for(String value : listString)
            System.out.print(value);
        System.out.println();

        //自增自减，可以是整数，也可以是浮点数
        jedis.set("age","17");
        //自增2
        jedis.incrBy("age",2);
        System.out.println(jedis.get("age"));
        //自减3
        jedis.decrBy("age",3);
        System.out.println(jedis.get("age"));

        //设定Key的存活时间
        jedis.setex("existKey",10,"existValue");
        while(jedis.exists("existKey")){
            System.out.println(jedis.get("existKey"));
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
