package project.runner.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RatingResponseDTO {
    private Long deliveryId;
    private Long runnerId;
    private Long sellerId;
    private Integer rating;
    private LocalDateTime createdAt;
}
