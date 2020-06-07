package pl.kacper.starzynski.cqrs.product.infrastructure;

import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kacper.starzynski.cqrs.product.domain.Product;
import pl.kacper.starzynski.cqrs.product.domain.ProductId;
import pl.kacper.starzynski.cqrs.product.domain.ProductRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public Product load(ProductId productId) {
        ProductDao productDao = productHibernateRepository.findById(productId.getId()).orElseThrow();
        return mapper.map(productDao, Product.class);
    }

    @Override
    public List<Product> loadAll() {
        List<ProductDao> products = productHibernateRepository.findAll();
        return products.stream().map(product -> mapper.map(product, Product.class)).collect(Collectors.toList());
    }
}
