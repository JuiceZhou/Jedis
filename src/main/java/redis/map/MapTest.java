package redis.map;

import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapTest {
    public static void main(String[] args) {
        //构建jedis客户端
        Jedis jedis = new Jedis("192.168.25.111",6379);
        Map<String,String> redisMap = new HashMap<String, String>();
        redisMap.put("field1","value1");
        redisMap.put("field2","value2");
        redisMap.put("field3","value3");
        //直接插入和取出map
        jedis.hmset("hmset",redisMap);
        //取出部分Map
        List<String> hmget = jedis.hmget("hmset","field2");
        for(String value : hmget)
            System.out.print(value);
        System.out.println("=====================");
        //逐个插入map
        jedis.hset("hset","field1","value1");
        jedis.hset("hset","field2","value2");
        jedis.hset("hset","field3","value3");
        //逐个取出
        String value1 = jedis.hget("hset", "field1");
        String value2 = jedis.hget("hset", "field2");
        String value3 = jedis.hget("hset", "field3");
        System.out.println(value1+":"+value2+":"+value3);
        System.out.println("=====================");
        //全部取出map
        Map<String, String> hsetMap = jedis.hgetAll("hset");
        Set<Map.Entry<String, String>> entrySet = hsetMap.entrySet();
        for(Map.Entry entry:entrySet){
            System.out.println(entry);
        }

    }
}
