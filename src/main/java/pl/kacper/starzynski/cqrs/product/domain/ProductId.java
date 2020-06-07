package pl.kacper.starzynski.cqrs.product.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class ProductId {
    UUID id = UUID.randomUUID();
}
