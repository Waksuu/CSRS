package pl.kacper.starzynski.cqrs.product.webui;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kacper.starzynski.cqrs.product.application.createproduct.CreateProductCommand;
import pl.kacper.starzynski.cqrs.product.application.createproduct.ProductCommandHandler;
import pl.kacper.starzynski.cqrs.product.webui.request.CreateProductRequest;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductCommandHandler productCommandHandler;

    @PostMapping
    public long createProduct(@RequestBody CreateProductRequest request) {
        CreateProductCommand command = getCreateProductCommand(request);
        return productCommandHandler.createProduct(command);

    }

    private CreateProductCommand getCreateProductCommand(CreateProductRequest request) {
        return new CreateProductCommand(request.getName(), request.getPrice(), request.getManufacturerEmail());
    }
}
