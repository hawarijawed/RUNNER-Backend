package project.runner.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.runner.DTOs.SellerCreateDTO;
import project.runner.DTOs.SellerResponseDTO;
import project.runner.DTOs.SellerUpdateDTO;
import project.runner.models.SellerProfile;
import project.runner.services.SellerProfileServices;

@RestController
@RequestMapping("/seller-profile")
@AllArgsConstructor
public class SellerProfileControllers {
    private final SellerProfileServices sellerProfileServices;

    @PostMapping("/{userId}")
    public ResponseEntity<SellerResponseDTO> createSellerProfile(
            @PathVariable Long userId,
            @Valid @RequestBody SellerCreateDTO sellerCreateDTO)
    {
        SellerResponseDTO sellerResponseDTO = sellerProfileServices.createSellerProfile(userId, sellerCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sellerResponseDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<SellerResponseDTO> getUserByUserId(@PathVariable Long userId){
        SellerResponseDTO dto = sellerProfileServices.getSellerByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> getSellerById(@PathVariable Long id){
        SellerResponseDTO sellerResponseDTO = sellerProfileServices.getSellerById(id);

        return ResponseEntity.status(HttpStatus.OK).body(sellerResponseDTO);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SellerResponseDTO> updateSellerByUserId(@PathVariable Long userId,
                                                                  @RequestBody SellerUpdateDTO sellerUpdateDTO)
    {
        SellerResponseDTO sellerResponseDTO = sellerProfileServices.updateSellerProfile(userId, sellerUpdateDTO);

        return  ResponseEntity.status(HttpStatus.OK)
                .body(sellerResponseDTO);
    }


}
