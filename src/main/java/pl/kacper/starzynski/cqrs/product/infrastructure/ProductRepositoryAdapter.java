package pl.kacper.starzynski.cqrs.product.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import pl.kacper.starzynski.cqrs.product.domain.Product;
import pl.kacper.starzynski.cqrs.product.domain.ProductId;
import pl.kacper.starzynski.cqrs.product.domain.ProductRepository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductHibernateRepository productHibernateRepository;

    @Override
    public ProductId save(Product product) {
        ProductDao productDao = new ProductDao();
        //TODO: Properties are not copied because product has VO as fields
        BeanUtils.copyProperties(product, productDao);
        UUID id = productHibernateRepository.save(productDao).getId();
        return new ProductId(id);
    }
}
