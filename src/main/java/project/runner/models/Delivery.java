package project.runner.models;

import jakarta.persistence.*;
import lombok.Data;
import project.runner.models.enumerates.DeliveryStatus;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerProfile seller;

    @ManyToOne
    @JoinColumn(name = "runner_id")
    private RunnerProfile runner;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal feeOffered;

    @Column(nullable = false)
    private String customerContact;

    @Column(nullable = false)
    private String pickupAddress;

    @Column(nullable = false)
    private Double pickupLatitude;

    @Column(nullable = false)
    private Double pickupLongitude;

    @Column(nullable = false)
    private String dropoffAddress;

    @Column(nullable = false)
    private Double dropoffLatitude;

    @Column(nullable = false)
    private Double dropoffLongitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status = DeliveryStatus.REQUESTED;

    @Version
    private Long version;


}
