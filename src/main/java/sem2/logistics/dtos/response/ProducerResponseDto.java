package sem2.logistics.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProducerResponseDto {

    private Integer producerId;

    private String address;

}
