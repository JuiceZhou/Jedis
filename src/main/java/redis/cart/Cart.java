package redis.cart;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.util.RedisInputStream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cart {
    private Jedis jedis;

    public void init(){
        //构建jedis客户端
        jedis = new Jedis("192.168.25.111",6379);
        //创建商品
        Product product1 = new Product("1645139266", "战地鳄2015秋冬新款马甲可脱卸帽休闲时尚无袖男士羽绒棉外套马甲", new BigDecimal("168"));
        Product product2 = new Product("1788744384", "天乐时 爸爸装加厚马甲秋冬装中年大码男士加绒马夹中老年坎肩老年人", new BigDecimal("40"));
        Product product3 = new Product("1645080454", "战地鳄2015秋冬新款马甲可脱卸帽休闲时尚无袖男士羽绒棉外套马甲", new BigDecimal("230"));
        //放入redis，以"shop:produce:productId"为key，对应的Product对象为value
        jedis.set("shop:produce:"+product1.getProductId(),new Gson().toJson(product1));
        jedis.set("shop:produce:"+product2.getProductId(),new Gson().toJson(product2));
        jedis.set("shop:produce:"+product3.getProductId(),new Gson().toJson(product3));

    }

    public static void main(String[] args) {

        String username = "zhouxiaomao";
        Cart cart = new Cart();
        cart.init();
        //往购物车中添加商品
        cart.addProductToCart(username,"1645139266",3);
        cart.addProductToCart(username,"1788744384",2);
        cart.addProductToCart(username,"1788744384",1);
        //获取购物车的商品列表
        List<Product> productList = cart.getProductList(username);
        for(Product product : productList){
            System.out.println(product);
        }
    }

    private List<Product> getProductList(String username) {
        List<Product> productList = new ArrayList<Product>();
        //从redis中获取对应购物车中的productId和productNum
        Map<String, String> cartMap = jedis.hgetAll("shop:cart:" + username);
        if(cartMap == null || cartMap.size() <= 0)
            return productList;
        Set<Map.Entry<String, String>> entrySet = cartMap.entrySet();
        for(Map.Entry entry : entrySet){
            Product product = new Product();
            String productId = (String)entry.getKey();
            product.setProductId(productId);
            Integer productNum = Integer.valueOf((String)entry.getValue());
            if(productNum > 0) {
                product.setProductNum(productNum);
            }else{
                product.setProductNum(0);
            }
            //补全product的信息
            completeProduct(product);
            productList.add(product);
        }
        return productList;
    }

    private void completeProduct(Product product) {
        //从redis中根据ProductId获取Product对象
        String productJson = jedis.get("shop:produce:" + product.getProductId());
        Product productRedis = new Gson().fromJson(productJson,Product.class);
        product.setProductName(productRedis.getProductName());
        product.setProductPrice(productRedis.getProductPrice());
    }

    private void addProductToCart(String username, String productId, int productNum) {
        //往"shop:cart:username"中加入数据
        jedis.hset("shop:cart:"+username,productId,productNum+"");
    }
}
