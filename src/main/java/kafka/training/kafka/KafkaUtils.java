package kafka.training.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class KafkaUtils {
    public final static String TRAINING_SYSTEM = "TrainingSystemState";
    public final static String TRAINING_MESSAGE = "TrainingMessage";

    public static Properties loadProperties() throws IOException {
        Properties p = new Properties();
        try (InputStream is = KafkaUtils.class.getClassLoader().getResourceAsStream("kafka.properties")) {
            p.load(is);
        }
        return p;
    }

    public static <T, S> KafkaProducer<String, T> createProducer(Class<S> valueSerializer) throws IOException {
        return createProducer(valueSerializer, false);
    }

    public static <T, S> KafkaProducer<String, T> createProducer(Class<S> valueSerializer, boolean sync) throws IOException {
        return createProducer(valueSerializer, sync, Collections.emptyMap());
    }

    public static <T, S> KafkaProducer<String, T> createProducer(Class<S> valueSerializer, boolean sync, Map<Object, Object> additionalProperties) throws IOException {
        Properties p = KafkaUtils.loadProperties();
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer.getCanonicalName());
        if (sync) {
            p.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        }
        p.putAll(additionalProperties);
        return new KafkaProducer<>(p);
    }

    public static <T, S> KafkaConsumer<String, T> createConsumer(Class<S> valueDeserializer, String groupId, boolean fromBeginning) throws IOException {
        return createConsumer(valueDeserializer, groupId, fromBeginning, Collections.emptyMap());
    }

    public static <T, S> KafkaConsumer<String, T> createConsumer(Class<S> valueDeserializer, String groupId, boolean fromBeginning, Map<Object, Object> additionalProperties) throws IOException {
        Properties p = KafkaUtils.loadProperties();
        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer.getCanonicalName());
        p.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        p.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, fromBeginning ? "earliest" : "latest"); // start at the beginning for the demo, else it would read nothing...
        p.putAll(additionalProperties);
        return new KafkaConsumer<>(p);
    }

}
