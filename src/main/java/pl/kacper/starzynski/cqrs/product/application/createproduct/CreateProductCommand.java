package pl.kacper.starzynski.cqrs.product.application.createproduct;

import lombok.Value;
import pl.kacper.starzynski.cqrs.sharedkernel.ExceptionMessages;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Value
public class CreateProductCommand {

    @Size(max = 25, message = ExceptionMessages.TOO_LONG_NAME)
    @NotBlank(message = ExceptionMessages.EMPTY_NAME)
    String name;

    @NotNull(message = ExceptionMessages.EMPTY_PRICE)
    @Positive(message = ExceptionMessages.PRICE_IS_NOT_POSITIVE)
    BigDecimal price;

    @NotBlank(message = ExceptionMessages.INVALID_EMAIL)
    @Email(message = ExceptionMessages.INVALID_EMAIL)
    String manufacturerEmail;
}
