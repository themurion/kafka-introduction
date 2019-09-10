package kafka.training.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

// FIXME this is not working correctly
@ConfigurationProperties("sign.app")
@Data
public class ApplicationConfig {

    @NotNull
    private String id = "rico-id";

    private String description = "rico-description";

    @NotEmpty
    private List<String> algorithms = Arrays.asList("cesar", "md5");
}
