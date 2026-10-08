package project.runner.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.runner.DTOs.SellerCreateDTO;
import project.runner.DTOs.SellerResponseDTO;
import project.runner.DTOs.SellerUpdateDTO;
import project.runner.exceptions.InvalidRoleException;
import project.runner.exceptions.SellerProfileAlreadyExistsException;
import project.runner.exceptions.SellerProfileNotFoundException;
import project.runner.exceptions.UserNotFoundException;
import project.runner.models.SellerProfile;
import project.runner.models.User;
import project.runner.models.enumerates.Roles;
import project.runner.repositories.SellerProfileRepository;
import project.runner.repositories.UserRepositories;

@Service
@AllArgsConstructor

public class SellerProfileServices {
    private final SellerProfileRepository sellerProfileRepository;
    private final UserRepositories userRepositories;

    @Transactional
    public SellerResponseDTO createSellerProfile(Long userId, SellerCreateDTO sellerCreateDTO){

        //find the user first
        User user = userRepositories.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("User Not found")
        );

        //Check the role of user
        if(user.getRole() != null){
            if(user.getRole() == Roles.SELLER){
                throw new SellerProfileAlreadyExistsException("The user is already assigned with Seller role");
            }

            throw  new InvalidRoleException("The user is already assigned with other role");
        }

        //Check if the entity already exists in Sellerprofile
        if(sellerProfileRepository.existsByUserId(userId)){
            throw new InvalidRoleException("Profile already exists in database");
        }

        user.setRole(Roles.SELLER);
        userRepositories.save(user);

        SellerProfile sellerProfile = new SellerProfile();
        sellerProfile.setUsers(user);
        sellerProfile.setShop_name(sellerCreateDTO.getShopName());
        sellerProfile.setCity(sellerCreateDTO.getCity());
        sellerProfile.setLongitude(sellerCreateDTO.getLongitude());
        sellerProfile.setLatitude(sellerCreateDTO.getLatitude());

        sellerProfileRepository.save(sellerProfile);
        return mapToSeller(sellerProfile);
    }

    public SellerResponseDTO getSellerByUserId(Long userId){
        SellerProfile sellerProfile = sellerProfileRepository.findByUserId(userId).orElseThrow(
                () -> new SellerProfileNotFoundException("Seller not found for given user id")
        );

        return mapToSeller(sellerProfile);
    }

    public SellerResponseDTO getSellerById(Long id){
        SellerProfile sellerProfile = sellerProfileRepository.findById(id).orElseThrow(
                () -> new SellerProfileNotFoundException("Seller not found with given user id")
        );

        return mapToSeller(sellerProfile);
    }

    @Transactional
    public SellerResponseDTO updateSellerProfile(Long userId, SellerUpdateDTO sellerUpdateDTO){

        SellerProfile sellerProfile = sellerProfileRepository.findByUserId(userId).orElseThrow(
                ()-> new SellerProfileNotFoundException("Seller profile not found for given user")
        );

        if(sellerUpdateDTO.getCity() != null){
            sellerProfile.setCity(sellerUpdateDTO.getCity());
        }
        if(sellerUpdateDTO.getShopName() != null){
            sellerProfile.setShop_name(sellerUpdateDTO.getShopName());
        }
        if(sellerUpdateDTO.getLatitude() != null){
            sellerProfile.setLatitude(sellerUpdateDTO.getLatitude());
        }
        if(sellerUpdateDTO.getLongitude() != null){
            sellerProfile.setLongitude(sellerUpdateDTO.getLongitude());
        }

        sellerProfileRepository.save(sellerProfile);

        return mapToSeller(sellerProfile);

    }

    private SellerResponseDTO mapToSeller(SellerProfile sellerProfile){
        SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
        sellerResponseDTO.setShopName(sellerProfile.getShop_name());
        sellerResponseDTO.setLongitude(sellerProfile.getLongitude());
        sellerResponseDTO.setLatitude(sellerProfile.getLatitude());
        sellerResponseDTO.setCity(sellerProfile.getCity());

        return sellerResponseDTO;
    }

}
