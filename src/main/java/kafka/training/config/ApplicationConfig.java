package kafka.training.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// FIXME this is not working correctly
@ConfigurationProperties("sign.app")
@Data
public class ApplicationConfig {

    @NotNull
    private String id;

    private String description;

    @NotEmpty
    private List<String> algorithms;

    @NotEmpty
    private Map<String, Object> kafkaProperties;

    private String applicationUuid = UUID.randomUUID().toString();
}
