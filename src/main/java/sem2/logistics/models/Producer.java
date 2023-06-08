package sem2.logistics.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer producerId;

    private String address;

    public Producer(String address) {
        this.address = address;
    }

}