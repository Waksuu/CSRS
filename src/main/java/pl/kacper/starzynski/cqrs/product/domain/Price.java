package pl.kacper.starzynski.cqrs.product.domain;

import pl.kacper.starzynski.cqrs.sharedkernel.BusinessRuleBrokenException;

import java.math.BigDecimal;

import static pl.kacper.starzynski.cqrs.sharedkernel.ExceptionMessages.PRICE_IS_NOT_POSITIVE;

public class Price {
    private static final String TWENTY_PERCENT = "1.2";
    private BigDecimal price;

    private Price() {

    }

    private Price(BigDecimal price) {
        this.price = price;
    }

    public static Price create(BigDecimal price) {
        if (priceIsNotPositive(price)) {
            throw new BusinessRuleBrokenException(PRICE_IS_NOT_POSITIVE);
        }

        return new Price(price);
    }

    public static Price create(String price) {
        return create(new BigDecimal(price));
    }

    private static boolean priceIsNotPositive(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) <= 0;
    }

    boolean isPriceBeingInflated(Price newPrice) {
        return newPriceExceededTwentyPercentOfPrevious(newPrice);
    }

    private boolean newPriceExceededTwentyPercentOfPrevious(Price newPrice) {
        return newPrice.price.compareTo(currentPriceTimesTwentyPercent()) > 0;
    }

    private BigDecimal currentPriceTimesTwentyPercent() {
        return this.price.multiply(new BigDecimal(TWENTY_PERCENT));
    }
}
