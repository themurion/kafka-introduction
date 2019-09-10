package kafka.training.systems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class SingleSignSystem {
    private String id;
    private String uuid;
    private String description;
    private List<String> algorithms;
}
