package pl.kacper.starzynski.cqrs.sharedkernel;

public class BusinessRuleBrokenException extends RuntimeException {
    public BusinessRuleBrokenException(String message) {
        super(message);
    }
}
