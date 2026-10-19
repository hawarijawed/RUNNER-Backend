package project.runner.DTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import project.runner.models.enumerates.DeliveryStatus;
import project.runner.models.enumerates.Roles;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonPropertyOrder({
        "id",
        "deliveryId",
        "changedByName",
        "role",
        "fromStatus",
        "toStatus",
        "changedAt"
})
public class DeliveryHistoryResponseDTO {

    private Long id;

    private Long deliveryId;

    private DeliveryStatus fromStatus;

    private DeliveryStatus toStatus;

    private String changedByName;

    private Roles role;

    private LocalDateTime changedAt;
}