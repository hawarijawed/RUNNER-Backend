package project.runner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.runner.models.DeliveryHistory;

import java.util.List;

@Repository
public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {
    List<DeliveryHistory> findByDeliveryIdOrderByChangedAtAsc(Long deliveryId);
    List<DeliveryHistory> findByChangedByIdOrderByChangedAtAsc(Long userId);
}
