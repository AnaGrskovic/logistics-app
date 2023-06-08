package sem2.logistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem2.logistics.models.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Integer> {

}
