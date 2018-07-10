package redis.cart;

import java.math.BigDecimal;

public class Product {
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productNum;

    public Product(String produceId, String productName, BigDecimal productPrice) {
        this.productId = produceId;
        this.productName = productName;
        this.productPrice = productPrice;
    }
    public Product(){

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
}

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productNum=" + productNum +
                '}';
    }
}
