package pl.kacper.starzynski.cqrs.product.webui.request;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class UpdatePriceRequest {
    BigDecimal price;
}
