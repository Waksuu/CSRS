package pl.kacper.starzynski.cqrs.product.application.createproduct;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.kacper.starzynski.cqrs.sharedkernel.InvalidCommandException;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductCommandHandlerTest {

    private static final String VALID_EMAIL = "email@domain.com";
    private static final BigDecimal VALID_PRICE = new BigDecimal("199.99");
    private static final BigDecimal VALID_PRICE_BEARLY_ABOVE_ZERO = new BigDecimal("0.001");
    private static final BigDecimal PRICE_WITH_VALUE_ZERO = new BigDecimal("0");
    private static final BigDecimal NEGATIVE_PRICE = new BigDecimal("-199.99");
    private static final String VALID_NAME = "Uber headphones";
    private static final String INVALID_LONG_NAME = "Uber headphones pro turbo manager controller utils seven helper the best in the world ever";
    private static final BigDecimal NO_PRICE = null;
    private static final String EMPTY_NAME = "";
    private static final String NO_NAME = null;
    private static final String EMPTY_EMAIL = "";
    private static final String NO_EMAIL = null;
    private static final String EMAIL_WITHOUT_AT_SIGN = "randomString";
    private static final String VALID_EMAIL_WITH_MANY_SUB_DOMAINS = "email@my.random.domain.com";
    private static final String EMAIL_WITHOUT_DOMAIN = "email@";
    private static final String MANY_INVALID_FIELDS = Stream.of(ProductCommandHandler.TOO_LONG_NAME,
            ProductCommandHandler.PRICE_IS_NOT_POSITIVE, ProductCommandHandler.INVALID_EMAIL)
            .collect(Collectors.joining(System.lineSeparator()));

    private final ProductCommandHandler productCommandHandler = new ProductCommandHandler();

    @ParameterizedTest(name = "#{index} - {2}")
    @MethodSource("createInvalidCommands")
    void shouldValidateCommandFields(CreateProductCommand command, String exceptionMessage, String testCaseName) {
        InvalidCommandException exception = assertThrows(InvalidCommandException.class, createProduct(command));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    private static Stream<Arguments> createInvalidCommands() {
        return Stream.of(
                Arguments.of(new CreateProductCommand(VALID_NAME, NEGATIVE_PRICE, VALID_EMAIL), ProductCommandHandler.PRICE_IS_NOT_POSITIVE, "Price is negative"),
                Arguments.of(new CreateProductCommand(VALID_NAME, NO_PRICE, VALID_EMAIL), ProductCommandHandler.EMPTY_PRICE, "Price is null"),
                Arguments.of(new CreateProductCommand(VALID_NAME, PRICE_WITH_VALUE_ZERO, VALID_EMAIL), ProductCommandHandler.PRICE_IS_NOT_POSITIVE, "Price value is 0"),

                Arguments.of(new CreateProductCommand(EMPTY_NAME, VALID_PRICE, VALID_EMAIL), ProductCommandHandler.EMPTY_NAME, "Name is empty"),
                Arguments.of(new CreateProductCommand(NO_NAME, VALID_PRICE, VALID_EMAIL), ProductCommandHandler.EMPTY_NAME, "Name is null"),
                Arguments.of(new CreateProductCommand(INVALID_LONG_NAME, VALID_PRICE, VALID_EMAIL), ProductCommandHandler.TOO_LONG_NAME, "Name is too long"),

                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, EMAIL_WITHOUT_AT_SIGN), ProductCommandHandler.INVALID_EMAIL, "Email does not have '@' sign"),
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, EMPTY_EMAIL), ProductCommandHandler.INVALID_EMAIL, "Email is empty"),
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, NO_EMAIL), ProductCommandHandler.INVALID_EMAIL, "Email is null"),
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, EMAIL_WITHOUT_DOMAIN), ProductCommandHandler.INVALID_EMAIL, "Email does not have domain"),

                Arguments.of(new CreateProductCommand(INVALID_LONG_NAME, NEGATIVE_PRICE, EMAIL_WITHOUT_DOMAIN), MANY_INVALID_FIELDS, "Multiple values are invalid")
        );
    }

    @ParameterizedTest(name = "#{index} - {1}")
    @MethodSource("createValidCommands")
    void shouldNotThrowWhenCommandIsValid(CreateProductCommand command, String testCaseName) {
        productCommandHandler.createProduct(command);
    }

    private static Stream<Arguments> createValidCommands() {
        return Stream.of(
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, VALID_EMAIL), "Valid command"),
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE_BEARLY_ABOVE_ZERO, VALID_EMAIL), "Valid price that is barely above zero"),
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, VALID_EMAIL_WITH_MANY_SUB_DOMAINS), "Valid email with many subdomains")
        );
    }

    private Executable createProduct(CreateProductCommand command) {
        return () -> productCommandHandler.createProduct(command);
    }
}