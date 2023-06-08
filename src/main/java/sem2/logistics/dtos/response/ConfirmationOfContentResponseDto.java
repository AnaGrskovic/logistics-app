package sem2.logistics.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sem2.logistics.util.State;

@Getter
@Setter
@AllArgsConstructor
public class ConfirmationOfContentResponseDto {

    private Integer confirmationOfContentId;

    private Integer productId;

    private Integer componentId;

    private State state;

    private String blockHash;

}
