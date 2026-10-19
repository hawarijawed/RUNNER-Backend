package project.runner.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.runner.DTOs.DeliveryHistoryResponseDTO;
import project.runner.DTOs.DeliveryResponseDTO;
import project.runner.exceptions.DeliveryHistoryNotFoundException;
import project.runner.models.Delivery;
import project.runner.models.DeliveryHistory;
import project.runner.models.User;
import project.runner.models.enumerates.DeliveryStatus;
import project.runner.repositories.DeliveryHistoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryHistoryServices {
    private final DeliveryHistoryRepository deliveryHistoryRepository;

    @Transactional
    public void recordDeliveryStatus(Delivery delivery, DeliveryStatus fromStatus, DeliveryStatus toStatus,User changedBy){
        DeliveryHistory deliveryHistory = new DeliveryHistory();
        deliveryHistory.setDelivery(delivery);
        deliveryHistory.setChangedBy(changedBy);
        deliveryHistory.setFromStatus(fromStatus);
        deliveryHistory.setToStatus(toStatus);

        deliveryHistoryRepository.save(deliveryHistory);
    }

    public List<DeliveryHistoryResponseDTO> getDeliveryHistoryByDeliveryId(Long deliveryId){
        List<DeliveryHistory> deliveryHistories = deliveryHistoryRepository.findByDeliveryIdOrderByChangedAtAsc(deliveryId);

        if(deliveryHistories.isEmpty()){
            throw new DeliveryHistoryNotFoundException("Delivery Record not found for given delivery id");
        }
        List<DeliveryHistoryResponseDTO> dtos = new ArrayList<>();
        for(DeliveryHistory deliveryHistory: deliveryHistories){
            dtos.add(mapDelivery(deliveryHistory));
        }

        return dtos;
    }

    public List<DeliveryHistoryResponseDTO> getDeliveryHistoryByUserId(Long userId){
        List<DeliveryHistory> deliveryHistories = deliveryHistoryRepository.findByChangedByIdOrderByChangedAtAsc(userId);

        if(deliveryHistories.isEmpty()){
            throw new DeliveryHistoryNotFoundException("Delivery History not found for given user Id");
        }

        List<DeliveryHistoryResponseDTO> deliveryHistoryResponseDTOS = new ArrayList<>();
        for(DeliveryHistory deliveryHistory: deliveryHistories){
            deliveryHistoryResponseDTOS.add(mapDelivery(deliveryHistory));
        }

        return deliveryHistoryResponseDTOS;
    }
    public DeliveryHistoryResponseDTO mapDelivery(DeliveryHistory deliveryHistory){
        DeliveryHistoryResponseDTO dto = new DeliveryHistoryResponseDTO();
        dto.setId(deliveryHistory.getId());
        dto.setDeliveryId(deliveryHistory.getDelivery().getId());
        dto.setFromStatus(deliveryHistory.getFromStatus());
        dto.setToStatus(deliveryHistory.getToStatus());
        dto.setChangedByName(deliveryHistory.getChangedBy().getName());
        dto.setRole(deliveryHistory.getChangedBy().getRole());
        dto.setChangedAt(deliveryHistory.getChangedAt());
        return dto;

    }
}
