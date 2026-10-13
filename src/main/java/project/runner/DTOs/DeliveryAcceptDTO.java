package project.runner.DTOs;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class DeliveryAcceptDTO {
    @NotBlank
    private String sellerName;

    @Id
    @NotNull
    private Long deliveryId;
    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal feeOffered;

    @NotBlank
    private String customerContact;

    @NotBlank
    private String pickupAddress;

    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double pickupLatitude;

    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double pickupLongitude;

    @NotBlank
    private String dropoffAddress;

    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double dropoffLatitude;

    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double dropoffLongitude;
}
