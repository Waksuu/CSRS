package pl.kacper.starzynski.cqrs.product.domain;

import pl.kacper.starzynski.cqrs.sharedkernel.BusinessRuleBrokenException;

import java.math.BigDecimal;

import static pl.kacper.starzynski.cqrs.sharedkernel.ExceptionMessages.PRICE_INFLATED;

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

    public void updatePrice(Price price) {
        if (price.isPriceBeingInflated(price)) {
            throw new BusinessRuleBrokenException(PRICE_INFLATED);
        }
        this.price = price;
    }
}
