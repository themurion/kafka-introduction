package kafka.training.sign;

import ch.srgssr.pdp.kafka.training.events.Signature;
import io.netty.buffer.ByteBuf;
import kafka.training.kafka.KafkaSigningService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.ByteBuffer;
import java.util.Base64;

@Singleton
public class SignService {
   @Inject
    KafkaSigningService kafkaSigningService;

    public SignedContent sign(SignedContent content) {
        byte[] c = Base64.getDecoder().decode(content.getContent());
        Signature reqUuid = kafkaSigningService.publishSignRequest(content.getAlgorithm(), ByteBuffer.wrap(c));

        return new SignedContent(
                reqUuid.getId(),
                reqUuid.getAlgorithm(),
                reqUuid.getKey(),
                base64ToString(reqUuid.getContent()),
                base64ToString(reqUuid.getSignature())
        );
    }

    private String base64ToString(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    private String base64ToString(ByteBuffer content) {
        return base64ToString(content.array());
    }

    public boolean verify(SignedContent content) {
        Signature result = kafkaSigningService.verifySignatureRequest(
                content.getAlgorithm(),
                content.getKey(),
                base64ToByteBuffer(content.getSignature()),
                base64ToByteBuffer(content.getContent())
        );
        return false;
    }

    private ByteBuffer base64ToByteBuffer(String base64) {
        return ByteBuffer.wrap(Base64.getDecoder().decode(base64));
    }
}
