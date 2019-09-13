package kafka.training.kafka;

import ch.srgssr.pdp.kafka.training.events.Info;
import io.micronaut.scheduling.annotation.Scheduled;
import kafka.training.config.ApplicationConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class ApplicationInfoNotifier {

    @Inject
    ApplicationConfig config;

    @Inject
    ProducersAndConsumers producers;

    @Scheduled(fixedDelay = "1m")
    public void informAboutSystem() {
        Info info = Info.newBuilder()
                .setAlgorithms(config.getAlgorithms())
                .setId(config.getId())
                .setUuid(UUID.randomUUID().toString()) // TODO make this consistent!
                .setDescription(config.getDescription())
                .build();

        producers.publishMessage(info.getId() + "/" + info.getUuid(), info);
    }
}
