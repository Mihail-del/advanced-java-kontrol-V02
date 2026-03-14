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


}
