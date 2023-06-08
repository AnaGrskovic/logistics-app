package sem2.logistics.models;

import lombok.AllArgsConstructor;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private Integer producerId;

    private String description;

    private State state;

    public Product(Integer producerId, String description, State state) {
        this.producerId = producerId;
        this.description = description;
        this.state = state;
    }

}