package pl.kacper.starzynski.cqrs.product.application.createproduct;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateProductCommand {
    String name;
    BigDecimal price;
    String manufacturerEmail;
}
