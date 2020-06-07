package pl.kacper.starzynski.cqrs.product.infrastructure;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
@Getter
public class ProductDao {
    @Id
    private UUID id;

    private String name;

    private BigDecimal price;

    private String manufacturerEmail;
}
