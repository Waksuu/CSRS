package pl.kacper.starzynski.cqrs.product.application.createproduct;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import pl.kacper.starzynski.cqrs.sharedkernel.ValidationResults;

import java.math.BigDecimal;

@Service
public class ProductCommandHandler {
    public static final String EMPTY_NAME = "Name cannot be empty";
    public static final String PRICE_IS_NOT_POSITIVE = "Price has to be a positive number";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String EMPTY_PRICE = "Price cannot be empty";
    public static final String TOO_LONG_NAME = "Product name cannot exceed 25 characters";
    private static final int MAX_NAME_LENGTH = 25;

    public long createProduct(CreateProductCommand command) {
        ValidationResults errors = new ValidationResults();
        validateCommand(command, errors);
        errors.throwOnError();

        System.out.println("It works!");
        System.out.println(command);
        return -1L;
    }

    private void validateCommand(CreateProductCommand command, ValidationResults errors) {
        if (command.getName() == null || command.getName().isBlank()) {
           errors.add(EMPTY_NAME);
        }

        if (command.getName() != null && command.getName().length() > MAX_NAME_LENGTH) {
           errors.add(TOO_LONG_NAME);
        }

        if (command.getPrice() == null) {
           errors.add(EMPTY_PRICE);
        }

        if (command.getPrice() != null && command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
           errors.add(PRICE_IS_NOT_POSITIVE);
        }

        if (!EmailValidator.getInstance().isValid(command.getManufacturerEmail())) {
           errors.add(INVALID_EMAIL);
        }
    }
}
