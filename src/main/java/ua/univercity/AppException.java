package ua.univercity;

// Base exception
public class AppException extends RuntimeException {
    public AppException(String msg) { super(msg); }
    public AppException(String msg, Throwable cause) { super(msg, cause); }
}

// Payment failed
class PaymentFailedException extends AppException {
    public PaymentFailedException(String msg) { super(msg); }
}

// Infrastructure issue
class InfrastructureException extends Exception {
    public InfrastructureException(String msg, Throwable cause) { super(msg, cause); }
}
