package ua.univercity;

public class Main {
    public static void main(String[] args) {
        // Mock inventory
        InventoryService inventory = items -> System.out.println("Stock reserved");

        // Init processor
        DefaultOrderProcessor processor = new DefaultOrderProcessor(inventory);

        // Mock card
        PaymentMethod cardPay = amount -> {
            if (amount.getAmount() > 25000L) throw new AppException("Card limit exceeded");
            System.out.println("Paid: " + amount.getAmount());
        };

        // Success order
        System.out.println("| Success ");
        try {
            OrderItem[] items1 = { new OrderItem("P1", new Money(5000L)) };
            Order order1 = new Order("O1", items1);

            processor.process(order1, cardPay);
            System.out.println("Final state: " + order1.getStatus());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Card limit fail
        System.out.println("\n| Card Fail");
        try {
            OrderItem[] items2 = { new OrderItem("P2", new Money(30000L)) };
            Order order2 = new Order("O2", items2);

            processor.process(order2, cardPay);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Max items fail
        System.out.println("\n| Items Fail ");
        try {
            OrderItem[] items3 = new OrderItem[11];
            for(int i = 0; i < 11; i++) items3[i] = new OrderItem("P", new Money(10L));
            Order order3 = new Order("O3", items3);

            processor.process(order3, cardPay);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}