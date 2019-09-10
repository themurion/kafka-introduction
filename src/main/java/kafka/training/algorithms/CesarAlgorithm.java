package kafka.training.algorithms;

import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class CesarAlgorithm implements Algorithm{
    @Override
    public String name() {
        return "Cesar";
    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public byte[] sign(byte[] original) {
        byte[] result = new byte[original.length];
        for (int i = 0; i < original.length; i++) {
            result[i] = (byte) ((original[i] + 2) & 0xFF);
        }
        return result;
    }

    @Override
    public boolean verify(byte[] original, byte[] signature) {
        return Arrays.equals(sign(original), signature);
    }
}
