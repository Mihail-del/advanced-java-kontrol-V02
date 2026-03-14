package ua.univercity;

public class OrderItem {
    private final String productId;
    private final Money price;

    public OrderItem(String productId, Money price) {
        this.productId = productId;
        this.price = price;
    }

    public Money getPrice() { return price; }

    public String getProductId() { return productId; }
}
