package pl.kacper.starzynski.cqrs.product.application.createproduct;

import an.awesome.pipelinr.Command.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kacper.starzynski.cqrs.product.domain.Product;
import pl.kacper.starzynski.cqrs.product.domain.ProductId;
import pl.kacper.starzynski.cqrs.product.domain.ProductRepository;

@Service
@RequiredArgsConstructor
public class CreateProductHandler implements Handler<CreateProductCommand, ProductId> {

    private final ProductRepository productRepository;

    @Override
    public ProductId handle(CreateProductCommand command) {
        Product product = Product.create(command.getName(), command.getPrice(), command.getManufacturerEmail());
        return productRepository.save(product);
    }
}
