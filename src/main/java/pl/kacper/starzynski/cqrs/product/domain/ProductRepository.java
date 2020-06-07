package pl.kacper.starzynski.cqrs.product.domain;

import java.util.List;

public interface ProductRepository {
    ProductId save(Product product);

    Product load(ProductId productId);

    List<Product> loadAll();
}
