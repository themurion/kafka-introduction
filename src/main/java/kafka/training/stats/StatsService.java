package kafka.training.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class StatsService {
    private final static Logger LOG = LoggerFactory.getLogger(StatsService.class);

    public Statistics statistics() {
        return new Statistics();
    }
}
