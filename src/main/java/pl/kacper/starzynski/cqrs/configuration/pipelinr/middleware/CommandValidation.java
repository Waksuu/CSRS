package pl.kacper.starzynski.cqrs.configuration.pipelinr.middleware;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Command.Middleware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import pl.kacper.starzynski.cqrs.sharedkernel.JsrValidator;
import pl.kacper.starzynski.cqrs.sharedkernel.ValidationResults;

@Service
@Order(1)
public class CommandValidation implements Middleware {
    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {
        ValidationResults validationResults = JsrValidator.validate(command);
        validationResults.throwOnError();
        return next.invoke();
    }
}
