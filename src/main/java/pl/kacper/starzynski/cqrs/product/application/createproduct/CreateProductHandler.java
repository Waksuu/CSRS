package pl.kacper.starzynski.cqrs.product.application.createproduct;

import an.awesome.pipelinr.Command.Handler;
import org.springframework.stereotype.Service;

@Service
public class CreateProductHandler implements Handler<CreateProductCommand, Long> {

    @Override
    public Long handle(CreateProductCommand command) {
        System.out.println(command);
        return -1L;
    }
}
