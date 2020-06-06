package pl.kacper.starzynski.cqrs.product.application.createproduct;

import org.springframework.stereotype.Service;
import pl.kacper.starzynski.cqrs.sharedkernel.ValidationResults;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductCommandHandler {

    public long createProduct(CreateProductCommand command) {
        ValidationResults errors = new ValidationResults();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CreateProductCommand>> violations = validator.validate(command);
        for (String violation : violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList())) {
            errors.add(violation);
        }
        errors.throwOnError();

        System.out.println("It works!");
        System.out.println(command);
        return -1L;
    }
}
