package com.example.gccoffee.exception;

public enum ProductException {
    NOT_FOUND("Product NOT_FOUND", 404),
    NOT_REGISTERED("Product NOT_REGISTERED", 400),
    NOT_MODIFIED("Product NOT_MODIFIED", 400),
    NOT_REMOVED("Product NOT_REMOVED", 400);

    private ProductTaskException productTaskException;

    ProductException(String message, int code) {
        productTaskException = new ProductTaskException(message, code);
    }

    public ProductTaskException get() {
        return productTaskException;
    }
}
