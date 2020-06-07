package pl.kacper.starzynski.cqrs.product.domain;

import java.math.BigDecimal;

public class Product {
    private final ProductId id;
    private final Name name;
    private final Price price;
    private final ManufacturerEmail manufacturerEmail;

    private Product(ProductId id, Name name, Price price, ManufacturerEmail manufacturerEmail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.manufacturerEmail = manufacturerEmail;
    }

    public static Product create(String name, BigDecimal price, String manufacturerEmail) {
        return new Product(new ProductId(), new Name(name), Price.create(price), ManufacturerEmail.create(manufacturerEmail));
    }
}
