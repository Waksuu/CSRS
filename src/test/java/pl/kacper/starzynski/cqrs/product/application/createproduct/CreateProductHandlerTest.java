package pl.kacper.starzynski.cqrs.product.application.createproduct;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import pl.kacper.starzynski.cqrs.configuration.pipelinr.middleware.CommandValidation;
import pl.kacper.starzynski.cqrs.product.domain.ProductRepository;
import pl.kacper.starzynski.cqrs.sharedkernel.ExceptionMessages;
import pl.kacper.starzynski.cqrs.sharedkernel.InvalidCommandException;
import pl.kacper.starzynski.cqrs.sharedkernel.ValidationResults;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.kacper.starzynski.cqrs.product.application.createproduct.CreateProductFields.createProductCommand;

class CreateProductHandlerTest {

    private static final BigDecimal VALID_PRICE_BEARLY_ABOVE_ZERO = new BigDecimal("0.001");
    private static final BigDecimal PRICE_WITH_VALUE_ZERO = new BigDecimal("0");
    private static final BigDecimal NEGATIVE_PRICE = new BigDecimal("-199.99");
    private static final String INVALID_LONG_NAME = "Uber headphones pro turbo manager controller utils seven helper the best in the world ever";
    private static final BigDecimal NO_PRICE = null;
    private static final String EMPTY_NAME = "";
    private static final String NO_NAME = null;
    private static final String EMPTY_EMAIL = "";
    private static final String NO_EMAIL = null;
    private static final String EMAIL_WITHOUT_AT_SIGN = "randomString";
    private static final String VALID_EMAIL_WITH_MANY_SUB_DOMAINS = "email@my.random.domain.com";
    private static final String EMAIL_WITHOUT_DOMAIN = "email@";
    private static final List<String> ALL_INVALID_FIELDS = List.of(ExceptionMessages.TOO_LONG_NAME,
            ExceptionMessages.PRICE_IS_NOT_POSITIVE, ExceptionMessages.INVALID_EMAIL);
    private static final List<String> SOME_INVALID_FIELDS = List.of(ExceptionMessages.EMPTY_NAME, ExceptionMessages.INVALID_EMAIL);
    private static final UnaryOperator<CreateProductFields.CreateProductFieldsBuilder> DEFAULT = UnaryOperator.identity();

    private static final Pipeline pipeline = createPipelinrWithCreateProductHandler();

    //TODO: This probably should be test only for command validation or end to end with spring boot context
    private static Pipelinr createPipelinrWithCreateProductHandler() {
        return new Pipelinr()
                .with(() -> Stream.of(new CreateProductHandler(Mockito.mock(ProductRepository.class))))
                .with(() -> Stream.of(new CommandValidation()));
    }

    @ParameterizedTest(name = "#{index} - {2}")
    @MethodSource("createInvalidCommands")
    void shouldThrowOnSingleInvalidField(CreateProductCommand command, String expectedMessage, String testCaseName) {
        InvalidCommandException exception = assertThrows(InvalidCommandException.class, createProduct(command));
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> createInvalidCommands() {
        return Stream.of(
                Arguments.of(createProductCommand(x -> x.price(NEGATIVE_PRICE)), ExceptionMessages.PRICE_IS_NOT_POSITIVE, "Price is negative"),
                Arguments.of(createProductCommand(x -> x.price(NO_PRICE)), ExceptionMessages.EMPTY_PRICE, "Price is null"),
                Arguments.of(createProductCommand(x -> x.price(PRICE_WITH_VALUE_ZERO)), ExceptionMessages.PRICE_IS_NOT_POSITIVE, "Price value is 0"),

                Arguments.of(createProductCommand(x -> x.name(EMPTY_NAME)), ExceptionMessages.EMPTY_NAME, "Name is empty"),
                Arguments.of(createProductCommand(x -> x.name(NO_NAME)), ExceptionMessages.EMPTY_NAME, "Name is null"),
                Arguments.of(createProductCommand(x -> x.name(INVALID_LONG_NAME)), ExceptionMessages.TOO_LONG_NAME, "Name is too long"),

                Arguments.of(createProductCommand(x -> x.manufacturerEmail(EMAIL_WITHOUT_AT_SIGN)), ExceptionMessages.INVALID_EMAIL, "Email does not have '@' sign"),
                Arguments.of(createProductCommand(x -> x.manufacturerEmail(EMPTY_EMAIL)), ExceptionMessages.INVALID_EMAIL, "Email is empty"),
                Arguments.of(createProductCommand(x -> x.manufacturerEmail(NO_EMAIL)), ExceptionMessages.INVALID_EMAIL, "Email is null"),
                Arguments.of(createProductCommand(x -> x.manufacturerEmail(EMAIL_WITHOUT_DOMAIN)), ExceptionMessages.INVALID_EMAIL, "Email does not have domain")
        );
    }

    @ParameterizedTest(name = "#{index} - {1}")
    @MethodSource("createValidCommands")
    void shouldNotThrowWhenCommandIsValid(CreateProductCommand command, String testCaseName) {
        pipeline.send(command);
    }

    private static Stream<Arguments> createValidCommands() {
        return Stream.of(
                Arguments.of(createProductCommand(DEFAULT), "Valid command"),
                Arguments.of(createProductCommand(x -> x.price(VALID_PRICE_BEARLY_ABOVE_ZERO)), "Valid price that is barely above zero"),
                Arguments.of(createProductCommand(x -> x.manufacturerEmail(VALID_EMAIL_WITH_MANY_SUB_DOMAINS)), "Valid email with many subdomains")
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
                Arguments.of(createProductCommand(x -> x.name(INVALID_LONG_NAME)
                        .price(NEGATIVE_PRICE)
                        .manufacturerEmail(EMAIL_WITHOUT_DOMAIN)), ALL_INVALID_FIELDS, "All values are invalid"),
                Arguments.of(createProductCommand(x -> x.name(EMPTY_NAME).manufacturerEmail(EMAIL_WITHOUT_AT_SIGN)),
                        SOME_INVALID_FIELDS, "Some of the values are invalid")
        );
    }

    private Executable createProduct(CreateProductCommand command) {
        return () -> pipeline.send(command);
    }

    private List<String> getListOfErrors(InvalidCommandException exception) {
        return List.of(exception.getMessage().split(ValidationResults.ERRORS_DELIMITER));
    }
}