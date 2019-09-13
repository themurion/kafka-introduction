package kafka.training.algorithms;

import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class StupidFailingAlgorithm implements Algorithm {
    @Override
    public String name() {
        return "Stupid";
    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public byte[] sign(byte[] original) {
        return original;
    }

    @Override
    public boolean verify(byte[] original, byte[] signature) {
        return Math.random() > 0.9;
    }
}
