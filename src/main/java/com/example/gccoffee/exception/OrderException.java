package com.example.gccoffee.exception;

public enum OrderException {
    NOT_FOUND("Order NOT_FOUND", 404),
    NOT_REGISTERED("Order NOT_REGISTERED", 400),
    NOT_MODIFIED("Order NOT_MODIFIED", 400),
    NOT_REMOVED("Order NOT_REMOVED", 400);

    private OrderTaskException orderTaskException;

    OrderException(String message, int code) {
        orderTaskException = new OrderTaskException(message, code);
    }

    public OrderTaskException get() {
        return orderTaskException;
    }
}
