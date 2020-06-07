package pl.kacper.starzynski.cqrs.product.domain;

import pl.kacper.starzynski.cqrs.sharedkernel.BusinessRuleBrokenException;

import java.math.BigDecimal;

import static pl.kacper.starzynski.cqrs.sharedkernel.ExceptionMessages.PRICE_IS_NOT_POSITIVE;

class Price {
    private BigDecimal price;

    private Price() {

    }

    private Price(BigDecimal price) {
        this.price = price;
    }

    static Price create(BigDecimal price) {
        if (priceIsNotPositive(price)) {
            throw new BusinessRuleBrokenException(PRICE_IS_NOT_POSITIVE);
        }

        return new Price(price);
    }

    private static boolean priceIsNotPositive(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) <= 0;
    }
}
