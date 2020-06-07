package pl.kacper.starzynski.cqrs.product.domain;

import java.math.BigDecimal;

public class Product {
    private ProductId id;
    private Name name;
    private Price price;
    private ManufacturerEmail manufacturerEmail;

    private Product() {
    }

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
