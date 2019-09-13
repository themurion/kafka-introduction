package kafka.training.kafka;

import ch.srgssr.pdp.kafka.training.events.Info;
import ch.srgssr.pdp.kafka.training.events.Signature;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface ProducersAndConsumers {
    @Topic(KafkaUtils.TRAINING_SYSTEM)
    void publishMessage(@KafkaKey String key, Info info);

    @Topic(KafkaUtils.TRAINING_MESSAGE)
    void publishMessage(@KafkaKey String key, Signature signature);
}
