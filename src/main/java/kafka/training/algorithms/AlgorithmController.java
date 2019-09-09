package kafka.training.algorithms;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import javax.inject.Inject;
import java.util.List;

@Controller("/algorithms")
public class AlgorithmController {

    @Inject
    AlgorithmService service;

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> algorithms() {
        return service.getAlgorithms();
    }
}
