package project.runner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.runner.models.Delivery;
import project.runner.models.enumerates.DeliveryStatus;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findBySellerId(Long sellerId);
    List<Delivery> findByRunnerId(Long runnerId);
    List<Delivery> findByStatus(DeliveryStatus status);
}
