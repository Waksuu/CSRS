package pl.kacper.starzynski.cqrs.sharedkernel;

import java.util.ArrayList;
import java.util.List;

public class ValidationResults {
    public static final String ERRORS_DELIMITER = System.lineSeparator();
    private final List<String> errors = new ArrayList<>();

    public void add(String errorMessage) {
        errors.add(errorMessage);
    }

    public void throwOnError() {
        if (errorOccurred()) {
            throw new InvalidCommandException(getErrorsJoinedByNewLine());
        }
    }

    private boolean errorOccurred() {
        return !errors.isEmpty();
    }

    private String getErrorsJoinedByNewLine() {
        return String.join(ERRORS_DELIMITER, errors);
    }
}
