package kafka.training.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Singleton
public class SystemService {
    private final static Logger LOG = LoggerFactory.getLogger(SystemService.class);

    public void updateSystem(SingleSignSystem system) {
        LOG.debug("Got an update for a system {}", system);
        systems.put(system.getUuid(), system);
    }

    // FIXME fill this with something useful
    private Map<String, SingleSignSystem> systems = new HashMap<>();

    private static SignSystemGroup apply(SingleSignSystem x) {
        return new SignSystemGroup(x.getId(), Collections.singletonList(x.getUuid()), x.getDescription(), x.getAlgorithms());
    }

    public List<String> getAlgorithms() {
        LOG.debug("Returning all current algorithms");
        return systems
                .values()
                .stream()
                .flatMap(x -> x.getAlgorithms().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<SignSystemGroup> getSystems() {
        LOG.debug("Returning all current systems");
        return new ArrayList<>(systems
                .values()
                .stream()
                .map(SystemService::apply)
                .collect(Collectors.toMap(SignSystemGroup::getId, v -> v, new BinaryOperator<SignSystemGroup>() {
                    @Override
                    public SignSystemGroup apply(SignSystemGroup sg1, SignSystemGroup sg2) {
                        ArrayList<String> m1 = new ArrayList<>();
                        m1.addAll(sg1.getUuid());
                        m1.addAll(sg2.getUuid());

                        ArrayList<String> m2 = new ArrayList<>();
                        m2.addAll(sg1.getAlgorithms());
                        m2.addAll(sg2.getAlgorithms());
                        return new SignSystemGroup(
                                sg1.getId(), m1, sg1.getDescription(), m2
                        );
                    }
                })).values());
    }


}
