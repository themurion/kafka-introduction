package kafka.training.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class Statistics {
    private int algorithmCount;
    private Map<String, Integer> algorithmUsage24H;
    private Map<String, Integer> algorithmUsage1H;
    private Map<String, Integer> algorithmUsage10Min;
    private Double successRate24H;
    private Double successRate1H;
    private Double successRate10Min;
    private Map<String, Double> success24H;
    private Map<String, Double> success1H;
    private Map<String, Double> success10Min;
}
