package pl.kacper.starzynski.cqrs.product.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductHibernateRepository extends JpaRepository<ProductDao, UUID> {
}
