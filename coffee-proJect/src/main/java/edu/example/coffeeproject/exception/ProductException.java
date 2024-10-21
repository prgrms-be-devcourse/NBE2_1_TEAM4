package edu.example.coffeeproject.exception;

public enum ProductException {
    NOT_FOUND_PRODUCT("find_No_Product", 404);


    private ProductTaskException productTaskException;

    ProductException(String message, Integer code) {
        productTaskException = new ProductTaskException(message, code);
    }


}
