package project.runner.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.runner.DTOs.DeliveryCreateDTO;
import project.runner.DTOs.DeliveryResponseDTO;
import project.runner.services.DeliveryServices;

import java.util.List;

@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryControllers {
    private final DeliveryServices deliveryServices;

    @PostMapping("/{userId}")
    public ResponseEntity<DeliveryResponseDTO> createDelivery(@PathVariable Long userId,
                                         @Valid @RequestBody DeliveryCreateDTO deliveryCreateDTO
    ){
        DeliveryResponseDTO deliveryResponseDTO = deliveryServices.createDelivery(userId, deliveryCreateDTO);
        return ResponseEntity.ok(deliveryResponseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<DeliveryResponseDTO>> getAllDeliveries(){
        List<DeliveryResponseDTO> deliveryResponseDTOList = deliveryServices.getAllDeliveries();
        return ResponseEntity.ok(deliveryResponseDTOList);
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponseDTO> getDeliveryById(@PathVariable Long deliveryId){
        DeliveryResponseDTO deliveryResponseDTO = deliveryServices.getDeliveryById(deliveryId);
        return ResponseEntity.ok(deliveryResponseDTO);
    }
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<DeliveryResponseDTO>> getAllDeliveryBySeller(@PathVariable Long sellerId){
        List<DeliveryResponseDTO> deliveryResponseDTOList = deliveryServices.getAllDeliveryBySellerId(sellerId);
        return ResponseEntity.ok(deliveryResponseDTOList);
    }

    @GetMapping("/runner/{runnerId}")
    public ResponseEntity<List<DeliveryResponseDTO>> getAllDeliveriesByRunner(@PathVariable Long runnerId){
        List<DeliveryResponseDTO> deliveryResponseDTOList = deliveryServices.getAllDeliveryByRunnerId(runnerId);
        return ResponseEntity.ok(deliveryResponseDTOList);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DeliveryResponseDTO>> getAvailableDeliveries(){
        List<DeliveryResponseDTO> deliveryResponseDTOList = deliveryServices.getAvailableDeliveries();
        return ResponseEntity.ok(deliveryResponseDTOList);
    }

    @PatchMapping("/{deliveryId}/accept/{userId}")
    public ResponseEntity<DeliveryResponseDTO> acceptDelivery(@PathVariable Long userId, @PathVariable Long deliveryId){
        DeliveryResponseDTO deliveryResponseDTO = deliveryServices.acceptDeliveryByRunner(userId, deliveryId);
        return ResponseEntity.ok(deliveryResponseDTO);
    }

    @PatchMapping("/{deliveryId}/pickup/{userId}")
    public ResponseEntity<DeliveryResponseDTO> markAsPickedUp(@PathVariable Long userId,@PathVariable Long deliveryId){
        DeliveryResponseDTO deliveryResponseDTO = deliveryServices.markedAsPickedUp(userId, deliveryId);
        return ResponseEntity.ok(deliveryResponseDTO);
    }

    @PatchMapping("/{deliveryId}/deliver/{userId}")
    public ResponseEntity<DeliveryResponseDTO> markAsDelivered(@PathVariable Long userId, @PathVariable Long deliveryId){
        DeliveryResponseDTO deliveryResponseDTO = deliveryServices.markedAsDelivered(userId, deliveryId);
        return ResponseEntity.ok(deliveryResponseDTO);
    }

    @PatchMapping("/{deliveryId}/cancel/{userId}")
    public ResponseEntity<DeliveryResponseDTO> cancelDelivery(@PathVariable Long userId, @PathVariable Long deliveryId){
        DeliveryResponseDTO deliveryResponseDTO = deliveryServices.cancelDelivery(userId, deliveryId);
        return  ResponseEntity.ok(deliveryResponseDTO);
    }

}
