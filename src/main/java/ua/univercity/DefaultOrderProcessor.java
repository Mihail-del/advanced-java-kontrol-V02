package ua.univercity;

public class DefaultOrderProcessor extends OrderProcessorTemplate {
    private final InventoryService inventoryService;

    public DefaultOrderProcessor(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Reserve items
    @Override
    protected void reserveStock(Order order) {
        try {
            inventoryService.reserve(order.getItems());
        }
        catch (Exception e) {
            throw new AppException("Reserve fail", e);
        }
    }

    // Pay
    @Override
    protected void processPayment(Order order, Money total, PaymentMethod pm) {
        pm.pay(total);
    }
}
