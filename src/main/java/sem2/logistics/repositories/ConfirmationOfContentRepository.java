package sem2.logistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sem2.logistics.models.ConfirmationOfContent;

@Repository
public interface ConfirmationOfContentRepository extends JpaRepository<ConfirmationOfContent, Integer> {

    @Query(value = "SELECT confirmation_of_content_id, product_id, component_id, state, block_hash " +
            " FROM confirmation_of_content " +
            " WHERE confirmation_of_content.product_id = ?1", nativeQuery = true)
    ConfirmationOfContent getConfirmationOfContentByProductId(Integer productId);

}