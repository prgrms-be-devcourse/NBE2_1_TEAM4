package com.example.coffee.exception;

public enum ProductException {
    NOT_MODIFIED("Product NOT_MODIFIED", 400),
    NOT_REMOVED("Product NOT_REMOVED", 402),
    NOT_FOUND("Product NOT_FOUND", 404),
    NOT_ADDED("Product NOT_ADDED", 405);


    private ProductTaskException productTaskException;

    ProductException(String message, int code) {
        productTaskException = new ProductTaskException(message, code);
    }

    public ProductTaskException get(){
        return productTaskException;
    }

}
