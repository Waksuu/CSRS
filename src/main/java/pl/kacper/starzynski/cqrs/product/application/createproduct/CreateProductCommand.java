package pl.kacper.starzynski.cqrs.product.application.createproduct;

import an.awesome.pipelinr.Command;
import lombok.Value;
import pl.kacper.starzynski.cqrs.product.domain.ProductId;

import javax.validation.constraints.*;
import java.math.BigDecimal;

import static pl.kacper.starzynski.cqrs.sharedkernel.ExceptionMessages.*;

@Value
public class CreateProductCommand implements Command<ProductId> {

    @Size(max = 25, message = TOO_LONG_NAME)
    @NotBlank(message = EMPTY_NAME)
    String name;

    @NotNull(message = EMPTY_PRICE)
    @Positive(message = PRICE_IS_NOT_POSITIVE)
    BigDecimal price;

    @NotBlank(message = INVALID_EMAIL)
    @Email(message = INVALID_EMAIL)
    String manufacturerEmail;
}
