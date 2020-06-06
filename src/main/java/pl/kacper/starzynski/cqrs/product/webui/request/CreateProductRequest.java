package pl.kacper.starzynski.cqrs.product.webui.request;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateProductRequest {
    String name;
    BigDecimal price;
    String manufacturerEmail;
}
