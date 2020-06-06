package pl.kacper.starzynski.cqrs.product.application.createproduct;

import org.springframework.stereotype.Service;
import pl.kacper.starzynski.cqrs.sharedkernel.JsrValidator;
import pl.kacper.starzynski.cqrs.sharedkernel.ValidationResults;

@Service
public class ProductCommandHandler {
    public long createProduct(CreateProductCommand command) {
        ValidationResults errors = JsrValidator.validate(command);
        errors.throwOnError();

        System.out.println(command);
        return -1L;
    }
}
