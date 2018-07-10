package redis.String;

import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.*;

/**
 * 对类进行序列化和反序列化
 */
public class ClassSerialize {
    private Jedis jedis;
    private Person person;
    @Before
    public void init(){
        //构建jedis客户端
        jedis = new Jedis("192.168.25.111",6379);

        person = new Person();
        person.setAge(20);
        person.setName("Tom");

    }

    /**
     * 对象序列化为JSON串
     */
    @Test
    public void serializeToJson(){
        //序列化为Json串
        jedis.set("class:json",new Gson().toJson(person));
        //反序列化
        String personJson = jedis.get("class:json");
        Person fromJson = new Gson().fromJson(personJson, Person.class);
        System.out.println(fromJson);
    }

    /**
     * 对象序列化为byte数组
     */
    @Test
    public void serializeToByte() throws Exception {
        //序列化
        byte[] personByte = getByte(person);
        //存入redis
        jedis.set("class:byte[]".getBytes(),personByte);
        //反序列化
        byte[] serializePerson = jedis.get("class:byte[]".getBytes());
        Person person = getPerson(serializePerson);
        System.out.println(person);
    }

    /**
     * 反序列化函数
     * @param serializePerson
     * @return
     */
    private Person getPerson(byte[] serializePerson) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(serializePerson);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Person) ois.readObject();

    }

    /**
     * 序列化函数
     * @param person
     * @return
     */
    private byte[] getByte(Person person) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(person);
        oos.flush();
        return bos.toByteArray();
    }

}
