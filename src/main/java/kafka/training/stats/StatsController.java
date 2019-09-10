package kafka.training.stats;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import javax.inject.Inject;

@Controller("/stats")
public class StatsController {
    @Inject
    StatsService service;

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Statistics statistics() {
        return service.statistics();
    }
}
