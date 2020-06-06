package pl.kacper.starzynski.cqrs.sharedkernel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationResults {
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
        return errors.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}
