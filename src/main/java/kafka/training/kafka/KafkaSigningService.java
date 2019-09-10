package kafka.training.kafka;

import ch.srgssr.pdp.kafka.training.events.EventType;
import ch.srgssr.pdp.kafka.training.events.Signature;
import kafka.training.algorithms.Algorithm;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Singleton
public class KafkaSigningService {
    @Inject
    private List<Algorithm> algorithms;

    @Inject
    ProducersAndConsumers producers;

    private Map<String, CountDownLatch> queues = new HashMap<>();
    private Map<String, Signature> responses = new HashMap<>();

    public Signature publishSignRequest(String algorithm, ByteBuffer content) {
        var uuid = UUID.randomUUID();

        var signatureRequest = Signature.newBuilder()
                .setUuid(uuid.toString())
                .setAlgorithm(algorithm)
                .setEvent(EventType.SIGN)
                .setContent(content)
                .build();

        producers.publishMessage(signatureRequest);

        // FIXME publish message

        return awaitAnswer(uuid);
    }

    private Signature awaitAnswer(UUID uuid) {
        CountDownLatch cl = new CountDownLatch(1);

        queues.put(uuid.toString(), cl);

        try {
            cl.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            queues.remove(uuid.toString());
            return null;
        }

        return responses.remove(uuid.toString());
    }

    public void publishSignedMessage(UUID uuid, String id, String algorithm, @Nullable String key, ByteBuffer signature, ByteBuffer content) {
        var signatureResponse = Signature.newBuilder()
                .setUuid(uuid.toString())
                .setId(id)
                .setAlgorithm(algorithm)
                .setContent(content)
                .setSignature(signature)
                .setKey(key)
                .setEvent(EventType.SIGNED)
                .build();

        // FIXME publish the signature
    }

    public void listenToSignatures(Signature signature) {
        var algorithm = signature.getAlgorithm();
        var algFound = (algorithms.stream().filter(v -> v.name().equals(algorithm)).findAny());
        switch (signature.getEvent()) {
            case VERIFICATION_FAILED:
            case VERIFIED:
            case SIGNED:
                if (queues.containsKey(signature.getUuid())) {
                    responses.put(signature.getUuid(), signature);
                    var cl = queues.remove(signature.getUuid());
                    cl.countDown();
                }
                break;
            case SIGN:
                algFound.ifPresent(alg -> {
                    var result = alg.sign(signature.getContent().array());
                    publishSignedMessage(UUID.fromString(signature.getUuid()), null, alg.name(), alg.key(), ByteBuffer.wrap(result), signature.getContent());
                });
                break;
            case VERIFY:
                algFound.ifPresent(alg -> {
                    var result = alg.verify(signature.getContent().array(), signature.getSignature().array());
                    publishVerificationResult(UUID.fromString(signature.getUuid()), result);
                });
                break;
        }
        // FIXME attach to topic
    }

    public Signature verifySignatureRequest(String algorithm, @Nullable String key, ByteBuffer signature, ByteBuffer content) {
        var uuid = UUID.randomUUID();
        var signatureRequest = Signature.newBuilder()
                .setUuid(uuid.toString())
                .setAlgorithm(algorithm)
                .setContent(content)
                .setSignature(signature)
                .setKey(key)
                .setEvent(EventType.VERIFY)
                .build();

        // FIXME publish the signature request
        return awaitAnswer(uuid);
    }

    public void publishVerificationResult(UUID uuid, boolean verified) {
        var signatureRequest = Signature.newBuilder()
                .setUuid(uuid.toString())
                .setEvent(verified ? EventType.VERIFIED : EventType.VERIFICATION_FAILED)
                .build();

        // FIXME publish the result
    }

}
