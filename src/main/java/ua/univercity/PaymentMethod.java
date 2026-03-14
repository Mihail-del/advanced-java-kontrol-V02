package ua.univercity;

// Pay interface
public interface PaymentMethod {
    void pay(Money amount);
}

// Card pay
class CardPayment implements PaymentMethod {
    @Override
    public void pay(Money amount) {
        if (amount.getAmount() > 25000L) {
            throw new AppException("Card limit exceeded");
        }
    }
}

// PayPal pay
class PayPalPayment implements PaymentMethod {
    @Override
    public void pay(Money amount) {
        if (amount.getAmount() < 200L) {
            throw new AppException("PayPal minimum is 200");
        }
    }
}

// Bank pay
class BankTransferPayment implements PaymentMethod {
    @Override
    public void pay(Money amount) {
        long fee = (long) (amount.getAmount() * 0.015);
        long total = amount.getAmount() + fee;
    }
}
