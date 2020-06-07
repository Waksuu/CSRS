package pl.kacper.starzynski.cqrs.product.domain;

public interface ProductRepository {
    ProductId save(Product product);
}
