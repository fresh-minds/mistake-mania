package nl.freshminds.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(long orderId) {
        super(String.format("Order '%s' could not be found.", orderId));
    }
}
