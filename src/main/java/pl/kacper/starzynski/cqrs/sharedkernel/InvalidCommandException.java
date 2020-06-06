package pl.kacper.starzynski.cqrs.sharedkernel;

public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String message) {
        super(message);
    }
}
