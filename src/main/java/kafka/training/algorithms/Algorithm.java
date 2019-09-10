package kafka.training.algorithms;

public interface Algorithm {
    String name();

    String key();

    byte[] sign(byte[] original);

    boolean verify(byte[] original, byte[] signature);
}
