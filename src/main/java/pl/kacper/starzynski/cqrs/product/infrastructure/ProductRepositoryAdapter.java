package pl.kacper.starzynski.cqrs.product.infrastructure;

import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kacper.starzynski.cqrs.product.domain.Product;
import pl.kacper.starzynski.cqrs.product.domain.ProductId;
import pl.kacper.starzynski.cqrs.product.domain.ProductRepository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductHibernateRepository productHibernateRepository;
    private final Mapper mapper;

    @Override
    public ProductId save(Product product) {
        ProductDao productDao = mapper.map(product, ProductDao.class);
        UUID id = productHibernateRepository.save(productDao).getId();
        return new ProductId(id);
    }
}
