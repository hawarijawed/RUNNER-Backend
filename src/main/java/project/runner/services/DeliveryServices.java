package project.runner.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.runner.DTOs.DeliveryAcceptDTO;
import project.runner.DTOs.DeliveryCreateDTO;
import project.runner.DTOs.DeliveryResponseDTO;
import project.runner.exceptions.*;
import project.runner.models.Delivery;
import project.runner.models.RunnerProfile;
import project.runner.models.SellerProfile;
import project.runner.models.enumerates.DeliveryStatus;
import project.runner.repositories.DeliveryRepository;
import project.runner.repositories.RunnerProfileRepository;
import project.runner.repositories.SellerProfileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DeliveryServices {
    private final DeliveryRepository deliveryRepository;
    private final SellerProfileRepository sellerProfileRepository;
    private final RunnerProfileRepository runnerProfileRepository;

    public DeliveryResponseDTO createDelivery(Long userId, DeliveryCreateDTO deliveryCreateDTO){
        SellerProfile sellerProfile = sellerProfileRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Seller profile not found"));

        Delivery delivery = new Delivery();
        delivery.setSeller(sellerProfile);
        delivery.setCustomerContact(deliveryCreateDTO.getCustomerContact());
        delivery.setFeeOffered(deliveryCreateDTO.getFeeOffered());

        delivery.setPickupAddress(deliveryCreateDTO.getPickupAddress());
        delivery.setPickupLatitude(deliveryCreateDTO.getPickupLatitude());
        delivery.setPickupLongitude(deliveryCreateDTO.getPickupLongitude());

        delivery.setDropoffAddress(deliveryCreateDTO.getDropoffAddress());
        delivery.setDropoffLatitude(deliveryCreateDTO.getDropoffLatitude());
        delivery.setDropoffLongitude(deliveryCreateDTO.getDropoffLongitude());
        deliveryRepository.save(delivery);
        return mapToDelivery(delivery);
    }

    public List<DeliveryResponseDTO> getAllDeliveryBySellerId(Long sellerId){
        List<Delivery> deliveryList = deliveryRepository.findBySellerId(sellerId);

        if(deliveryList.isEmpty()){
            throw new RuntimeException("No delivery found !");
        }
        List<DeliveryResponseDTO> deliveryResponseDTOList = new ArrayList<>();
        for(Delivery delivery: deliveryList){
            deliveryResponseDTOList.add(mapToDelivery(delivery));
        }

        return deliveryResponseDTOList;
    }

    public List<DeliveryResponseDTO> getAllDeliveryByRunnerId(Long runnerId){
        List<Delivery> deliveryList = deliveryRepository.findByRunnerId(runnerId);
        if(deliveryList.isEmpty()){
            throw new RuntimeException("No delivery found");
        }
        List<DeliveryResponseDTO> deliveryResponseDTOList = new ArrayList<>();
        for(Delivery delivery: deliveryList){
            deliveryResponseDTOList.add(mapToDelivery(delivery));
        }

        return deliveryResponseDTOList;
    }

    public List<DeliveryResponseDTO> getAllDeliveries(){
        List<Delivery> deliveryList = deliveryRepository.findAll();
        if(deliveryList.isEmpty()){
            throw new RuntimeException("No delivery found");
        }

        List<DeliveryResponseDTO> deliveryResponseDTOList = new ArrayList<>();

        for(Delivery delivery: deliveryList){
            deliveryResponseDTOList.add(mapToDelivery(delivery));
        }

        return deliveryResponseDTOList;
    }

    //accept delivery by runner
    //TOPIC FOR DISCUSSION
    @Transactional
    public DeliveryResponseDTO acceptDeliveryByRunner(Long userId, Long deliveryId){
        RunnerProfile runnerProfile = runnerProfileRepository.findByUserId(userId).orElseThrow(
                ()-> new RunnerProfileNotFoundException("Runner Does not exist")
        );

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                ()-> new DeliveryNotFoundException("Deliver does not exist for given ID")
        );

        //Check if the delivery is available for acceptance
        if(delivery.getStatus() != DeliveryStatus.REQUESTED){
            throw new DeliveryAlreadyAcceptedException("Delivery already accepted");
        }

        delivery.setStatus(DeliveryStatus.MATCHED);
        delivery.setRunner(runnerProfile);
        deliveryRepository.saveAndFlush(delivery);

        return mapToDelivery(delivery);
    }

    //Cancel Delivery
    @Transactional
    public DeliveryResponseDTO cancelDelivery(Long userId, Long deliveryId){
        //Verify user
        SellerProfile sellerProfile = sellerProfileRepository.findByUserId(userId).orElseThrow(
                () -> new SellerProfileNotFoundException("Seller profile not found for given user id")
        );
        //Verify delivery
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                ()-> new DeliveryNotFoundException("Delivery not found for given id")
        );

        if(!Objects.equals(delivery.getSeller().getId(), sellerProfile.getId())){
            throw new UnauthorizedDeliveryActionException("You are not authorized to cancel this delivery");
        }
        // 4. Validate cancellation status
        if (delivery.getStatus() != DeliveryStatus.REQUESTED &&
                delivery.getStatus() != DeliveryStatus.MATCHED) {

            throw new DeliveryCancellationNotAllowedException(
                    "Delivery cannot be cancelled in its current status"
            );
        }

        delivery.setStatus(DeliveryStatus.CANCELLED);
        deliveryRepository.save(delivery);
        return mapToDelivery(delivery);
    }
    private DeliveryResponseDTO mapToDelivery(Delivery delivery){
        DeliveryResponseDTO deliveryResponseDTO = new DeliveryResponseDTO();

        deliveryResponseDTO.setDeliveryId(delivery.getId());
        deliveryResponseDTO.setSellerName(delivery.getSeller().getUsers().getName());
        //runnerName is assigned only after runner accepts delivery.
        if(delivery.getRunner() != null){
            deliveryResponseDTO.setRunnerName(delivery.getRunner().getUser().getName());
        }
        deliveryResponseDTO.setCustomerContact(delivery.getCustomerContact());
        deliveryResponseDTO.setFeeOffered(delivery.getFeeOffered());

        deliveryResponseDTO.setPickupAddress(delivery.getPickupAddress());
        deliveryResponseDTO.setPickupLatitude(delivery.getPickupLatitude());
        deliveryResponseDTO.setPickupLongitude(delivery.getPickupLongitude());

        deliveryResponseDTO.setDropoffAddress(delivery.getDropoffAddress());
        deliveryResponseDTO.setDropoffLatitude(delivery.getDropoffLatitude());
        deliveryResponseDTO.setDropoffLongitude(delivery.getDropoffLongitude());
        deliveryResponseDTO.setDeliveryStatus(delivery.getStatus().toString());

        return deliveryResponseDTO;
    }

}
