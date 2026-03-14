package ua.univercity;

public class Order {
    private final String id;
    private OrderItem[] items;
    private OrderStatus status;

    public Order(String id, OrderItem[] items) {
        this(id, items, OrderStatus.NEW);
    }

    public Order(String id, OrderItem[] items, OrderStatus status) {
        this.id = id;
        this.status = status;

        if (items != null) {
            this.items = items.clone();
        } else {
            this.items = new OrderItem[0];
        }
    }

    public String getId() { return id; }

    public OrderItem[] getItems() { return items.clone(); }

    public OrderStatus getStatus() { return status; }

    // Updating state
    public void transitionTo(OrderStatus newStatus) {
        if (this.status == OrderStatus.NEW && (newStatus == OrderStatus.CANCELLED || newStatus == OrderStatus.PAID)) {
            this.status = newStatus;
        } else if (this.status == OrderStatus.PAID && newStatus == OrderStatus.SHIPPED) {
            this.status = newStatus;
        } else if (this.status == OrderStatus.SHIPPED && newStatus == OrderStatus.DELIVERED) {
            this.status = newStatus;
        } else {
            throw new IllegalStateException("Bad state transition");
        }
    }
}