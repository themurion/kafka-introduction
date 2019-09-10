package kafka.training.systems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class SignSystemGroup {
    private String id;
    private List<String> uuid;
    private String description;
    private List<String> algorithms;
}
