package sem2.logistics.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sem2.logistics.util.State;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class ConfirmationOfContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer confirmationOfContentId;

    private Integer productId;

    private Integer componentId;

    private State state;

    private String blockHash;

    public ConfirmationOfContent(Integer productId, Integer componentId, State state, String blockHash) {
        this.productId = productId;
        this.componentId = componentId;
        this.state = state;
        this.blockHash = blockHash;
    }

}
