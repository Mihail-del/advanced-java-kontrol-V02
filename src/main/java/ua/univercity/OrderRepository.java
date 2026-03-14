package ua.univercity;

import java.util.Optional;

// Repo API
public interface OrderRepository {
    Optional<Order> findById(String id);
}
