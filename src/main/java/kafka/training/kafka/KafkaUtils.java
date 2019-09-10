package kafka.training.kafka;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import kafka.training.config.ApplicationConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Properties;

@Singleton
public class KafkaUtils {
    public final static String TRAINING_SYSTEM = "TrainingSystemState";
    public final static String TRAINING_MESSAGE = "TrainingMessage";

    @Inject
    ApplicationConfig config;

    public <T> KafkaProducer<String, T> createAvroProducer(String clientId) {
        Properties p = new Properties();
        p.putAll(config.getKafkaProperties());
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getCanonicalName());
        p.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        // p.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        p.putAll(Collections.emptyMap());
        return new KafkaProducer<>(p);
    }

    public <T> KafkaConsumer<String, T> createAvroConsumer(String groupId, String clientId) {
        Properties p = new Properties();
        p.putAll(config.getKafkaProperties());
        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getCanonicalName());
        p.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        p.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        p.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // start at the beginning for the demo, else it would read nothing...
        p.putAll(Collections.emptyMap());
        return new KafkaConsumer<>(p);
    }
}
