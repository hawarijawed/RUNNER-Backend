package project.runner.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.runner.DTOs.RatingCreateDTO;
import project.runner.DTOs.RatingResponseDTO;
import project.runner.exceptions.*;
import project.runner.models.Delivery;
import project.runner.models.Rating;
import project.runner.models.RunnerProfile;
import project.runner.models.SellerProfile;
import project.runner.models.enumerates.DeliveryStatus;
import project.runner.repositories.DeliveryRepository;
import project.runner.repositories.RatingRepository;
import project.runner.repositories.RunnerProfileRepository;
import project.runner.repositories.SellerProfileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RatingServices {
    private final RatingRepository ratingRepository;
    private final DeliveryRepository deliveryRepository;
    private final SellerProfileRepository sellerProfileRepository;
    private final RunnerProfileRepository runnerProfileRepository;

    @Transactional
    public RatingResponseDTO addRating(Long deliveryId, Long sellerId, RatingCreateDTO ratingCreateDTO){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new DeliveryNotFoundException("Delivery does not exist with given ID")
        );

        //Delivery Status validation
        if(delivery.getStatus() != DeliveryStatus.DELIVERED){
            throw new InvalidDeliveryStatusException("Only deliveries with status: Delivered can be rated");
        }
        //verifying seller owns delivery or not
        if(!sellerId.equals(delivery.getSeller().getId())){
            throw new UnauthorizedDeliveryActionException("You are not authorized to rate this delivery");
        }

        //Verifying duplicate rating or concurrent rating
        if(ratingRepository.existsByDeliveryId(deliveryId)){
            throw new DeliveryAlreadyRatedException("This delivery has already been rated");
        }

        RunnerProfile runnerProfile = delivery.getRunner();

        //If runner exists or not
        if(!runnerProfileRepository.existsById(runnerProfile.getId())){
            throw new RunnerProfileNotFoundException("Runner profile not found associated with given delivery");
        }

        runnerProfile.setRatingSum(
                runnerProfile.getRatingSum() + ratingCreateDTO.getRating()
        );
        runnerProfile.setRatingCount(
                runnerProfile.getRatingCount() + 1
        );

        runnerProfileRepository.save(runnerProfile);
        Integer rates = Math.toIntExact(runnerProfile.getRatingSum() / runnerProfile.getRatingCount());
        Rating rating = new Rating();
        rating.setRating(ratingCreateDTO.getRating());
        rating.setDelivery(delivery);
        ratingRepository.save(rating);

        return mapToRating(rating);
    }

    //Fetching a rating and accessing lazy relationships @Transactional(readOnly = true) is recommended
    @Transactional(readOnly = true)
    public RatingResponseDTO getRatingByDeliveryId(Long deliveryId){
        Rating rating = ratingRepository.findByDelivery_Id(deliveryId).orElseThrow(
                () -> new RatingNotFoundException("This delivery has no rating")
        );
        return mapToRating(rating);
    }

    @Transactional(readOnly = true)
    public List<RatingResponseDTO> getRatingByRunnerId(Long runnerId){
        List<Rating> ratingList = ratingRepository.findByDelivery_Runner_Id(runnerId);
        List<RatingResponseDTO> ratingResponseDTOS = new ArrayList<>();
        for(Rating rating: ratingList){
            ratingResponseDTOS.add(mapToRating(rating));
        }

        return ratingResponseDTOS;
    }
    private RatingResponseDTO mapToRating(Rating rating){
        RatingResponseDTO ratingResponseDTO = new RatingResponseDTO();
        ratingResponseDTO.setRating(rating.getRating());
        ratingResponseDTO.setDeliveryId(rating.getDelivery().getId());
        ratingResponseDTO.setSellerId(rating.getDelivery().getSeller().getId());
        ratingResponseDTO.setRunnerId(rating.getDelivery().getRunner().getId());
        ratingResponseDTO.setCreatedAt(rating.getRatedAt());

        return ratingResponseDTO;
    }
}
