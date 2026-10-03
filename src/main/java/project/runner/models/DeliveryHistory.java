package project.runner.models;

import jakarta.persistence.*;
import lombok.Data;
import project.runner.models.enumerates.DeliveryStatus;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "delivery_histories")
public class DeliveryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    @Column(nullable = false)
    private Delivery delivery;

    @Enumerated( EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus fromStatus;
    @Enumerated( EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus toStatus;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false)
    private User changedBy;
}