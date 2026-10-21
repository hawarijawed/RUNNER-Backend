package project.runner.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.runner.DTOs.RatingCreateDTO;
import project.runner.DTOs.RatingResponseDTO;
import project.runner.services.RatingServices;

import java.util.List;

@RestController
@RequestMapping("/rating")
@AllArgsConstructor
public class RatingControllers {
    private final RatingServices ratingServices;

    @PostMapping("/{sellerId}/rate/{deliveryId}")
    public ResponseEntity<RatingResponseDTO> addRating(@PathVariable Long sellerId,
                                                       @PathVariable Long deliveryId,
                                                       @RequestBody @Valid RatingCreateDTO ratingCreateDTO
    ){
        RatingResponseDTO responseDTO = ratingServices.addRating(deliveryId, sellerId, ratingCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<RatingResponseDTO> getRatingByDelivery(@PathVariable Long deliveryId){
        RatingResponseDTO responseDTO = ratingServices.getRatingByDeliveryId(deliveryId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/runner/{runnerId}")
    public ResponseEntity<List<RatingResponseDTO>> getRatingByRunner(@PathVariable Long runnerId){
        List<RatingResponseDTO> responseDTO = ratingServices.getRatingByRunnerId(runnerId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
