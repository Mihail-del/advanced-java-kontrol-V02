package ua.univercity;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class OrderProcessorTemplate {
    private static final Logger log = Logger.getLogger(OrderProcessorTemplate.class.getName());

    // Main process
    public final void process(Order order, PaymentMethod paymentMethod) {
        try {
            log.info("Start order: " + order.getId());
            validate(order);
            reserveStock(order);
            Money total = calculateTotal(order);
            processPayment(order, total, paymentMethod);
            completeOrder(order);
            log.info("Done order: " + order.getId());
        } catch (AppException e) {
            log.warning("Business fail: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "System fail", e);
            throw new RuntimeException("Error", e);
        }
    }

    // Validate size
    protected void validate(Order order) {
        if (order.getItems().length > 10) throw new AppException("Max 10 items");
    }

    // Abstract reserve
    protected abstract void reserveStock(Order order) throws AppException;

    // Calc total
    protected Money calculateTotal(Order order) {
        long sum = 0L;
        for (OrderItem item : order.getItems()) {
            sum += item.getPrice().getAmount();
        }
        if (sum >= 10000L) {
            sum = (long) (sum * 0.95);
        }
        return new Money(sum);
    }

    // Abstract pay
    protected abstract void processPayment(Order order, Money total, PaymentMethod pm);

    // Finish order
    protected void completeOrder(Order order) {
        order.transitionTo(OrderStatus.PAID);
    }
}