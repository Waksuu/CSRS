package pl.kacper.starzynski.cqrs.sharedkernel;

public class ExceptionMessages {
    public static final String EMPTY_NAME = "Name cannot be empty";
    public static final String PRICE_IS_NOT_POSITIVE = "Price has to be a positive number";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String EMPTY_PRICE = "Price cannot be empty";
    public static final String TOO_LONG_NAME = "Product name cannot exceed 25 characters";
    public static final String PRICE_INFLATED = "Price cannot be increased for more than 20% within 2 months";
    public static final String EMPTY_PRODUCT_ID = "Product id cannot be empty";
}
