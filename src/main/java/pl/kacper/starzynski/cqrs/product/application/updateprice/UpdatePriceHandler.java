package pl.kacper.starzynski.cqrs.product.application.updateprice;

import an.awesome.pipelinr.Command.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.kacper.starzynski.cqrs.product.domain.Price;
import pl.kacper.starzynski.cqrs.product.domain.Product;
import pl.kacper.starzynski.cqrs.product.domain.ProductId;
import pl.kacper.starzynski.cqrs.product.domain.ProductRepository;

@Transactional
@RequiredArgsConstructor
public class UpdatePriceHandler implements Handler<UpdatePriceCommand, Void> {
    private final ProductRepository productRepository;

    @Override
    public Void handle(UpdatePriceCommand command) {
        Product product = productRepository.load(new ProductId(command.getProductId()));
        product.updatePrice(Price.create(command.getPrice()));
        productRepository.save(product);
        return null;
    }
}
