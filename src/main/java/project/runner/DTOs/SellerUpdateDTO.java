package project.runner.DTOs;

import lombok.Data;

@Data
public class SellerUpdateDTO {
    private String shopName;
    private String city;
    private Double latitude;
    private Double longitude;
}
