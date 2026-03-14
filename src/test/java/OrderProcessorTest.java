import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
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

    // Neg max items
    @Test
    void testMaxItems() {
        OrderItem[] items = new OrderItem[11];
        for (int i=0; i<11; i++) items[i] = new OrderItem("P", new Money(1L));
        Order order = new Order("5", items);
        assertThrows(AppException.class, () -> processor.process(order, cardPay));
    }

    // Neg out of stock
    @Test
    void testOutOfStock() {
        processor = new DefaultOrderProcessor(i -> { throw new OutOfStockException("Out of stock"); });
        Order order = new Order("6", new OrderItem[]{new OrderItem("P1", new Money(10L))});

        assertThrows(OutOfStockException.class, () -> processor.process(order, cardPay));
    }

    // Neg card limit
    @Test
    void testCardLimit() {
        Order order = new Order("7", new OrderItem[]{new OrderItem("P1", new Money(30000L))});
        assertThrows(AppException.class, () -> processor.process(order, cardPay));
    }

    // Neg paypal min
    @Test
    void testPayPalMin() {
        Order order = new Order("8", new OrderItem[]{new OrderItem("P1", new Money(150L))});
        assertThrows(AppException.class, () -> processor.process(order, payPal));
    }

    // Neg bad state
    @Test
    void testBadState() {
        Order order = new Order("9", new OrderItem[0], OrderStatus.SHIPPED);
        assertThrows(IllegalStateException.class, () -> order.transitionTo(OrderStatus.CANCELLED));
    }

    // Neg chain
    @Test
    void testChainException() {
        processor = new DefaultOrderProcessor(i -> { throw new Exception("DB fail"); });
        Order order = new Order("10", new OrderItem[]{new OrderItem("P1", new Money(10L))});
        AppException ex = assertThrows(AppException.class, () -> processor.process(order, cardPay));
        assertNotNull(ex.getCause());
    }

    // Params states
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = {"PAID", "CANCELLED"})
    void testStates(OrderStatus target) {
        Order order = new Order("3", new OrderItem[0]);
        order.transitionTo(target);
        assertEquals(target, order.getStatus());
    }
}
