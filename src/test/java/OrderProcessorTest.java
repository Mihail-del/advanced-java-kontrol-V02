import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.univercity.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    private DefaultOrderProcessor processor;
    private InventoryService mockInventory;

    // Setup tests
    @BeforeEach
    void setUp() {
        mockInventory = items -> {};
        processor = new DefaultOrderProcessor(mockInventory);
    }

    // Mock card
    PaymentMethod cardPay = amount -> {
        if (amount.getAmount() > 25000L) throw new AppException("Fail");
    };

    // Mock paypal
    PaymentMethod payPal = amount -> {
        if (amount.getAmount() < 200L) throw new AppException("Fail");
    };

    // Success card
    @Test
    void testSuccessCard() {
        Order order = new Order("1", new OrderItem[]{new OrderItem("P1", new Money(500L))});
        processor.process(order, cardPay);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    // Success discount
    @Test
    void testDiscount() {
        Order order = new Order("2", new OrderItem[]{new OrderItem("P1", new Money(10000L))});
        final long[] charge = {0L};
        processor.process(order, amount -> charge[0] = amount.getAmount());
        assertEquals(9500L, charge[0]);
    }

    // Success paypal
    @Test
    void testSuccessPayPal() {
        Order order = new Order("4", new OrderItem[]{new OrderItem("P1", new Money(300L))});
        assertDoesNotThrow(() -> processor.process(order, payPal));
    }
}
