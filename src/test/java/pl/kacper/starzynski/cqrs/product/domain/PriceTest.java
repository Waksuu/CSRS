package pl.kacper.starzynski.cqrs.product.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO: Fix me - data driven tests
class PriceTest {

    @Test
    void isPriceBeingInflated() {
        // GIVEN
        Price currentPrice = Price.create("100");
        Price updatedPrice = Price.create("115");

        // WHEN
        boolean result = currentPrice.isPriceBeingInflated(updatedPrice);

        // THEN
        assertFalse(result);
    }

    @Test
    void isPriceBeingInflated_2() {
        // GIVEN
        Price currentPrice = Price.create("100");
        Price updatedPrice = Price.create("120");

        // WHEN
        boolean result = currentPrice.isPriceBeingInflated(updatedPrice);

        // THEN
        assertFalse(result);
    }

    @Test
    void isPriceBeingInflated_3() {
        // GIVEN
        Price currentPrice = Price.create("100");
        Price updatedPrice = Price.create("125");

        // WHEN
        boolean result = currentPrice.isPriceBeingInflated(updatedPrice);

        // THEN
        assertTrue(result);
    }
}