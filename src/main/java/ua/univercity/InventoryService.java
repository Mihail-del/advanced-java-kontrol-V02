package ua.univercity;

// Inventory API
public interface InventoryService {
    void reserve(OrderItem[] items) throws Exception;
}
