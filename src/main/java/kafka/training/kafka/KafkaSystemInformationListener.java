package kafka.training.kafka;

import ch.srgssr.pdp.kafka.training.events.Info;
import io.micronaut.scheduling.annotation.Scheduled;
import kafka.training.systems.SingleSignSystem;
import kafka.training.systems.SystemService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class KafkaSystemInformationListener {

    @Inject
    SystemService service;

    @Scheduled(fixedDelay = "1m")
    public void consumeKafkaListener() {
        service.updateSystem(
                new SingleSignSystem("id", "uuid", "description", Collections.singletonList("Cesar"))
        );
    }

    // FIXME this needs to be implemented in reality
    public void consumeKafkaListener(Info info) {
        service.updateSystem(
                new SingleSignSystem(
                        info.getId(),
                        info.getUuid(),
                        info.getDescription(),
                        info.getAlgorithms()
                )
        );
    }
}
