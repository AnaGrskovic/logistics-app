package sem2.logistics.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sem2.logistics.util.State;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponseDto {

    private Integer productId;

    private Integer producerId;

    private String description;

    private State state;

}