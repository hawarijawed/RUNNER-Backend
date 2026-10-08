package project.runner.DTOs;

import lombok.Data;

@Data
public class SellerResponseDTO {
    private String shopName;
    private String city;
    private Double latitude;
    private Double longitude;
}
