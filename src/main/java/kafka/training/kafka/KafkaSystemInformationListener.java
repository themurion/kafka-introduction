package kafka.training.kafka;

import ch.srgssr.pdp.kafka.training.events.Info;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.OffsetStrategy;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import kafka.training.systems.SingleSignSystem;
import kafka.training.systems.SystemService;

import javax.inject.Inject;

@KafkaListener(
        groupId = "tr-rico-system",
        clientId = "tr-rico-s1",
        offsetReset = OffsetReset.EARLIEST,
        offsetStrategy = OffsetStrategy.ASYNC
)
public class KafkaSystemInformationListener {

    @Inject
    SystemService service;

    @Topic(KafkaUtils.TRAINING_SYSTEM)
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
