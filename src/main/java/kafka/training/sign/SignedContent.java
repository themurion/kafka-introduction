package kafka.training.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class SignedContent {
    private String id;
    private String algorithm;
    private String key;
    private String content;
    private String signature;
}
