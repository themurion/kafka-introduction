package kafka.training.kafka;

import ch.srgssr.pdp.kafka.training.events.Info;
import io.micronaut.scheduling.annotation.Scheduled;
import kafka.training.config.ApplicationConfig;
import kafka.training.systems.SystemService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class ApplicationInfoNotifier {

    @Inject
    SystemService service;

    @Inject
    ApplicationConfig config;

    @Scheduled(fixedDelay = "1m")
    public void informAboutSystem() {
        // FIXME publish Info message
        var info = Info.newBuilder()
                .setAlgorithms(config.getAlgorithms())
                .setId(config.getId())
                .setUuid(UUID.randomUUID().toString()) // TODO make this consistent!
                .setDescription(config.getDescription())
                .build();
    }
}
