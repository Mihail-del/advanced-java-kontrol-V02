import org.junit.jupiter.api.BeforeEach;
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
}
