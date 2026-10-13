package project.runner.DTOs;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class DeliveryCreateDTO {
    @NotBlank
    private String sellerName;
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
/*
Delivery
│
├── id
├── seller ────────────► SellerProfile
├── runner ────────────► RunnerProfile (nullable)
├── feeOffered
├── customerContact
│
├── pickupAddress
├── pickupLatitude
├── pickupLongitude
│
├── dropoffAddress
├── dropoffLatitude
├── dropoffLongitude
│
├── status
├── version
└── createdAt
*/