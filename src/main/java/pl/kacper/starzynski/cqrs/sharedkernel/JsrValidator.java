package pl.kacper.starzynski.cqrs.sharedkernel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

//TODO: Can this be non-static?
public class JsrValidator {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> ValidationResults validate(T object) {
        ValidationResults validationResults = new ValidationResults();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        for (ConstraintViolation<T> violation : violations) {
            validationResults.add(violation.getMessage());
        }
        return validationResults;
    }
}
