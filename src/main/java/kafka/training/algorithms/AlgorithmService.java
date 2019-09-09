package kafka.training.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Singleton
public class AlgorithmService {
    private final static Logger LOG = LoggerFactory.getLogger(AlgorithmService.class);

    // FIXME fill this with something useful
    private List<String> algorithms = Collections.emptyList();

    public List<String> getAlgorithms() {
        LOG.debug("Returning all current algorithms");
        return algorithms;
    }
}
