package pl.kacper.starzynski.cqrs.product.application.updateprice;

import an.awesome.pipelinr.Command;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

import static pl.kacper.starzynski.cqrs.sharedkernel.ExceptionMessages.*;

@Value
public class UpdatePriceCommand implements Command<Void> {

    @NotNull(message = EMPTY_PRODUCT_ID)
    UUID productId;

    //TODO: Make custom jsr
    @NotNull(message = EMPTY_PRICE)
    @Positive(message = PRICE_IS_NOT_POSITIVE)
    BigDecimal price;
}
