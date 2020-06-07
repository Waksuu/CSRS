package pl.kacper.starzynski.cqrs.product.webui;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kacper.starzynski.cqrs.product.application.createproduct.CreateProductCommand;
import pl.kacper.starzynski.cqrs.product.webui.request.CreateProductRequest;

import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final Pipeline pipeline;

    @PostMapping
    public UUID createProduct(@RequestBody CreateProductRequest request) {
        return pipeline.send(getCreateProductCommand(request)).getId();
    }

    private CreateProductCommand getCreateProductCommand(CreateProductRequest request) {
        return new CreateProductCommand(request.getName(), request.getPrice(), request.getManufacturerEmail());
    }
}
