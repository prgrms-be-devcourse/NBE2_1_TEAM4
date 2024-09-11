package edu.example.coffeeproject.exception;

public enum OrderException {
    NOT_FOUND_ORDER("Order Not Found", 404),
    NOT_FOUND_ORDERITEM("OrderItem Not Found", 404);


    private OrderTaskException orderTaskException;

    OrderException(String message, int code) {
        orderTaskException = new OrderTaskException(message, code);
    }

    public OrderTaskException get() {
        return orderTaskException;
    }
}
