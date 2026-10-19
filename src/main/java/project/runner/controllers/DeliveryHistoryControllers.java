package project.runner.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.runner.DTOs.DeliveryHistoryResponseDTO;
import project.runner.DTOs.DeliveryResponseDTO;
import project.runner.services.DeliveryHistoryServices;

import java.util.List;

@RestController
@RequestMapping("/delivery-history")
@AllArgsConstructor
public class DeliveryHistoryControllers {
    private final DeliveryHistoryServices deliveryHistoryServices;

    @GetMapping("/{deliveryId}")
    public ResponseEntity<List<DeliveryHistoryResponseDTO>> getDeliveryRecordByDeliveryId(@PathVariable Long deliveryId){
        List<DeliveryHistoryResponseDTO> responseDTOS = deliveryHistoryServices.getDeliveryHistoryByDeliveryId(deliveryId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTOS);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeliveryHistoryResponseDTO>> getDeliveryRecordByUserId(@PathVariable Long userId){
        List<DeliveryHistoryResponseDTO> responseDTOS = deliveryHistoryServices.getDeliveryHistoryByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTOS);
    }

}
