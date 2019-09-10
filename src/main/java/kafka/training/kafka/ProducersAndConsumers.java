package kafka.training.kafka;

import ch.srgssr.pdp.kafka.training.events.Info;
import ch.srgssr.pdp.kafka.training.events.Signature;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceShutdownEvent;
import kafka.training.config.ApplicationConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class ProducersAndConsumers implements ApplicationEventListener<ServiceShutdownEvent>  {
    @Inject
    ApplicationConfig config;

    @Inject
    KafkaUtils kafkaUtils;

    private KafkaProducer<String, Signature> trainingMessageProducer;
    private KafkaProducer<String, Info> trainingSystemProducer;

    private KafkaConsumer<String, Signature> trainingMessageConsumer;
    private KafkaConsumer<String, Info> trainingSystemConsumer;

    public void publishMessage(Info info) {
        // TODO implement this
    }

    public void publishMessage(Signature signature) {
        // TODO implement this
    }

    @PostConstruct
    public void setup() {
        try {
            trainingMessageProducer = kafkaUtils.createAvroProducer(config.getApplicationUuid());
            trainingSystemProducer = kafkaUtils.createAvroProducer(config.getApplicationUuid());

            trainingMessageConsumer = kafkaUtils.createAvroConsumer(config.getId(), config.getApplicationUuid());
            trainingSystemConsumer = kafkaUtils.createAvroConsumer(config.getId(), config.getApplicationUuid());

            trainingMessageConsumer.subscribe(Collections.singleton(KafkaUtils.TRAINING_SYSTEM));
            trainingSystemConsumer.subscribe(Collections.singleton(KafkaUtils.TRAINING_MESSAGE));

            startEndlessLoop();
        } catch (Exception e) {
            System.exit(2);
        }

        // TODO shut down if loop is not starting up
    }

    private void startEndlessLoop() {
        // TODO poll the topics!
    }

    @Override
    public void onApplicationEvent(ServiceShutdownEvent event) {
        try {
            trainingMessageConsumer.unsubscribe();
            trainingSystemConsumer.unsubscribe();

            trainingMessageConsumer.close();
            trainingSystemConsumer.close();

            trainingMessageProducer.close();
            trainingSystemProducer.close();
        } catch (Exception e) {
            System.exit(2);
        }
    }

}
