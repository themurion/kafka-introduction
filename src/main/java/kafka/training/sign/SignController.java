package kafka.training.sign;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;

@Controller("/")
public class SignController {
    @Inject
    SignService service;

    @Post("/sign")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SignedContent sign(@Body SignedContent content) {
        return service.sign(content);
    }

    @Post("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpStatus verify(@Body SignedContent content) {
        return service.verify(content) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
}
