package kafka.training.kafka;

import ch.srgssr.pdp.kafka.training.events.Info;
import ch.srgssr.pdp.kafka.training.events.Signature;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceStartedEvent;
import kafka.training.config.ApplicationConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.UUID;

@Singleton
public class ProducersAndConsumers implements ApplicationEventListener<ServiceStartedEvent> {

    @Inject
    ApplicationConfig config;
    private KafkaProducer<String, Info> trainingMessageProducer;
    private KafkaProducer<String, Signature> trainingSystemProducer;

    private KafkaConsumer<String, Info> trainingMessageConsumer;
    private KafkaConsumer<String, Signature> trainingSystemConsumer;

    public void informAboutSystem() {
        // FIXME publish Info message
        var info = Info.newBuilder()
                .setAlgorithms(config.getAlgorithms())
                .setId(config.getId())
                .setUuid(UUID.randomUUID().toString()) // TODO make this consistent!
                .setDescription(config.getDescription())
                .build();
    }

    @Override
    public void onApplicationEvent(ServiceStartedEvent event) {
        try {
            trainingMessageProducer = KafkaUtils.createProducer(KafkaAvroSerializer.class);
            trainingSystemProducer = KafkaUtils.createProducer(KafkaAvroSerializer.class);

            trainingMessageConsumer = KafkaUtils.createConsumer(KafkaAvroDeserializer.class, config.getId(), true);
            trainingSystemConsumer = KafkaUtils.createConsumer(KafkaAvroDeserializer.class, config.getId(), true);

            trainingMessageConsumer.subscribe(Collections.singleton("TrainingSystemState"));
            trainingSystemConsumer.subscribe(Collections.singleton("TrainingMessage"));

            startEndlessLoop();
        } catch (Exception e) {
            System.exit(2);
        }

        // TODO shut down if loop is not running
    }

    private void startEndlessLoop() {
        // TODO poll the topics!
    }
}
