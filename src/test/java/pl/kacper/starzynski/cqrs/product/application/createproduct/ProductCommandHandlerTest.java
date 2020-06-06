package pl.kacper.starzynski.cqrs.product.application.createproduct;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.kacper.starzynski.cqrs.sharedkernel.ExceptionMessages;
import pl.kacper.starzynski.cqrs.sharedkernel.InvalidCommandException;
import pl.kacper.starzynski.cqrs.sharedkernel.ValidationResults;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
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
    private static final List<String> MANY_INVALID_FIELDS = List.of(ExceptionMessages.TOO_LONG_NAME,
            ExceptionMessages.PRICE_IS_NOT_POSITIVE, ExceptionMessages.INVALID_EMAIL);

    private final ProductCommandHandler productCommandHandler = new ProductCommandHandler();

    @ParameterizedTest(name = "#{index} - {2}")
    @MethodSource("createInvalidCommands")
    void shouldThrowOnSingleInvalidField(CreateProductCommand command, String expectedMessage, String testCaseName) {
        InvalidCommandException exception = assertThrows(InvalidCommandException.class, createProduct(command));
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> createInvalidCommands() {
        return Stream.of(
                Arguments.of(new CreateProductCommand(VALID_NAME, NEGATIVE_PRICE, VALID_EMAIL), ExceptionMessages.PRICE_IS_NOT_POSITIVE, "Price is negative"),
                Arguments.of(new CreateProductCommand(VALID_NAME, NO_PRICE, VALID_EMAIL), ExceptionMessages.EMPTY_PRICE, "Price is null"),
                Arguments.of(new CreateProductCommand(VALID_NAME, PRICE_WITH_VALUE_ZERO, VALID_EMAIL), ExceptionMessages.PRICE_IS_NOT_POSITIVE, "Price value is 0"),

                Arguments.of(new CreateProductCommand(EMPTY_NAME, VALID_PRICE, VALID_EMAIL), ExceptionMessages.EMPTY_NAME, "Name is empty"),
                Arguments.of(new CreateProductCommand(NO_NAME, VALID_PRICE, VALID_EMAIL), ExceptionMessages.EMPTY_NAME, "Name is null"),
                Arguments.of(new CreateProductCommand(INVALID_LONG_NAME, VALID_PRICE, VALID_EMAIL), ExceptionMessages.TOO_LONG_NAME, "Name is too long"),

                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, EMAIL_WITHOUT_AT_SIGN), ExceptionMessages.INVALID_EMAIL, "Email does not have '@' sign"),
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, EMPTY_EMAIL), ExceptionMessages.INVALID_EMAIL, "Email is empty"),
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, NO_EMAIL), ExceptionMessages.INVALID_EMAIL, "Email is null"),
                Arguments.of(new CreateProductCommand(VALID_NAME, VALID_PRICE, EMAIL_WITHOUT_DOMAIN), ExceptionMessages.INVALID_EMAIL, "Email does not have domain")
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

    @ParameterizedTest(name = "#{index} - {2}")
    @MethodSource("createInvalidCommandsWithMultipleInvalidValues")
    void shouldThrowOnMultipleInvalidFields(CreateProductCommand command, List<String> expectedMessages, String testCaseName) {
        InvalidCommandException exception = assertThrows(InvalidCommandException.class, createProduct(command));
        assertThat(getListOfErrors(exception)).containsExactlyInAnyOrder(expectedMessages.toArray(String[]::new));
    }

    private static Stream<Arguments> createInvalidCommandsWithMultipleInvalidValues() {
        return Stream.of(
                Arguments.of(new CreateProductCommand(INVALID_LONG_NAME, NEGATIVE_PRICE, EMAIL_WITHOUT_DOMAIN), MANY_INVALID_FIELDS, "Multiple values are invalid")
        );
    }

    private Executable createProduct(CreateProductCommand command) {
        return () -> productCommandHandler.createProduct(command);
    }

    private List<String> getListOfErrors(InvalidCommandException exception) {
        return List.of(exception.getMessage().split(ValidationResults.ERRORS_DELIMITER));
    }
}