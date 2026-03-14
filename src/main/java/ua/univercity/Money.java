package ua.univercity;

import java.util.Objects;

public final class Money {
    private final long amount;

    public Money(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Negative amount");
        }
        this.amount = amount;
    }

    public long getAmount() { return amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == 0;
    }

    @Override
    public int hashCode() { return Objects.hash(amount); }

    @Override
    public String toString() { return "Money{" + amount + "}"; }
}
