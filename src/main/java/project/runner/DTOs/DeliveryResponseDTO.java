package project.runner.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class DeliveryResponseDTO {
    private Long deliveryId;
    private String sellerName;
    private String runnerName;
    private BigDecimal feeOffered;
    private String customerContact;
    private String pickupAddress;
    private Double pickupLatitude;
    private Double pickupLongitude;
    private String dropoffAddress;
    private Double dropoffLatitude;
    private Double dropoffLongitude;
    private String deliveryStatus;
}
