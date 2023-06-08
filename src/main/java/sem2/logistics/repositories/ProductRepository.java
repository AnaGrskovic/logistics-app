package sem2.logistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem2.logistics.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}