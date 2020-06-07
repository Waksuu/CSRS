package pl.kacper.starzynski.cqrs.product.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductId {
    private UUID id = UUID.randomUUID();

    public ProductId() {
    }

    public ProductId(UUID id) {
        this.id = id;
    }
}
