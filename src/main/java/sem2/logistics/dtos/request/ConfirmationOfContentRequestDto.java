package sem2.logistics.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sem2.logistics.util.State;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationOfContentRequestDto {

    private Integer productId;

    private Integer componentId;

    private State state;

}
