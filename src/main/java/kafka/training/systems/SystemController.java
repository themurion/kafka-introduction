package kafka.training.systems;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import javax.inject.Inject;
import java.util.List;

@Controller("/")
public class SystemController {
    @Inject
    SystemService service;

    @Get("/algorithms")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> algorithms() {
        return service.getAlgorithms();
    }

    @Get("/systems")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SignSystemGroup> systems() {
        return service.getSystems();
    }
}
