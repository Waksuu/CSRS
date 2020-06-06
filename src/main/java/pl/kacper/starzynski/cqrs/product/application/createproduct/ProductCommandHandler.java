package pl.kacper.starzynski.cqrs.product.application.createproduct;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import pl.kacper.starzynski.cqrs.sharedkernel.InvalidCommandException;

import java.math.BigDecimal;

@Service
public class ProductCommandHandler {
    public static final String EMPTY_NAME = "Name cannot be empty";
    public static final String EMPTY_EMAIL = "Email cannot be empty";
    public static final String PRICE_IS_NOT_POSITIVE = "Price has to be a positive number";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String EMPTY_PRICE = "Price cannot be empty";
    public static final String TOO_LONG_NAME = "Product name cannot exceed 25 characters";

    public long createProduct(CreateProductCommand command) {
        if (command.getName() == null || command.getName().isBlank()) {
            throw new InvalidCommandException(EMPTY_NAME);
        }

        if (command.getName().length() > 25) {
            throw new InvalidCommandException(TOO_LONG_NAME);
        }

        if (command.getManufacturerEmail() == null || command.getManufacturerEmail().isBlank()) {
            throw new InvalidCommandException(EMPTY_EMAIL);
        }

        if (command.getPrice() == null) {
            throw new InvalidCommandException(EMPTY_PRICE);
        }

        if (command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidCommandException(PRICE_IS_NOT_POSITIVE);
        }

        if (!EmailValidator.getInstance().isValid(command.getManufacturerEmail())) {
            throw new InvalidCommandException(INVALID_EMAIL);
        }

        System.out.println("It works!");
        System.out.println(command);
        return -1L;
    }
}
