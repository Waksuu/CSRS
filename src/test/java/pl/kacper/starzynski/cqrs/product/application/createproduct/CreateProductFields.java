package pl.kacper.starzynski.cqrs.product.application.createproduct;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

@Builder
class CreateProductFields {
    private static final String VALID_EMAIL = "email@domain.com";
    private static final BigDecimal VALID_PRICE = new BigDecimal("199.99");
    private static final String VALID_NAME = "Uber headphones";

    private String name;
    private BigDecimal price;
    private String manufacturerEmail;

    static CreateProductCommand createProductCommand(UnaryOperator<CreateProductFieldsBuilder> valuesToOverride) {
        var validCreateProductCommand = prepareDefaultValidCommandValues();
        var overriddenCommand = overrideDefaultValuesWithUserInput(valuesToOverride, validCreateProductCommand);
        return createCommand(overriddenCommand);
    }

    private static CreateProductFieldsBuilder prepareDefaultValidCommandValues() {
        return CreateProductFields.builder()
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .manufacturerEmail(VALID_EMAIL);
    }

    private static CreateProductFields overrideDefaultValuesWithUserInput(
            UnaryOperator<CreateProductFieldsBuilder> valuesToOverride,
            CreateProductFieldsBuilder validCreateProductCommand) {
        return valuesToOverride.apply(validCreateProductCommand).build();
    }

    private static CreateProductCommand createCommand(CreateProductFields overriddenCommand) {
        return new CreateProductCommand(overriddenCommand.name, overriddenCommand.price, overriddenCommand.manufacturerEmail);
    }
}
