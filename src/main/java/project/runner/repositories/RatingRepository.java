package project.runner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.runner.models.Rating;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByDeliveryId(Long deliveryId);
    List<Rating> findByDelivery_Runner_Id(Long runnerId);
    Optional<Rating> findByDelivery_Id(Long deliveryId);
}
