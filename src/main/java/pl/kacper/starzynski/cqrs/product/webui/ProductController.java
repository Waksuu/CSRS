package pl.kacper.starzynski.cqrs.product.webui;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kacper.starzynski.cqrs.product.application.createproduct.CreateProductCommand;
import pl.kacper.starzynski.cqrs.product.domain.Product;
import pl.kacper.starzynski.cqrs.product.domain.ProductRepository;
import pl.kacper.starzynski.cqrs.product.webui.request.CreateProductRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final Pipeline pipeline;
    private final ProductRepository productRepository;

    @PostMapping
    public UUID createProduct(@RequestBody CreateProductRequest request) {
        return pipeline.send(getCreateProductCommand(request)).getId();
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.loadAll();
    }

    private CreateProductCommand getCreateProductCommand(CreateProductRequest request) {
        return new CreateProductCommand(request.getName(), request.getPrice(), request.getManufacturerEmail());
    }
}
