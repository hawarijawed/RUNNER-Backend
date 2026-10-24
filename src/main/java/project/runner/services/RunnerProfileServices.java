package project.runner.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.runner.DTOs.RunnerResponseDTO;
import project.runner.exceptions.InvalidRoleException;
import project.runner.exceptions.RunnerProfileAlreadyExistsException;
import project.runner.exceptions.RunnerProfileNotFoundException;
import project.runner.exceptions.UserNotFoundException;
import project.runner.models.RunnerProfile;
import project.runner.models.User;
import project.runner.models.enumerates.Roles;
import project.runner.repositories.RunnerProfileRepository;
import project.runner.repositories.UserRepositories;

@Service
@AllArgsConstructor
public class RunnerProfileServices {
    private final RunnerProfileRepository runnerProfileRepository;
    private final UserRepositories userRepositories;

    @Transactional
    public RunnerResponseDTO createRunnerProfile(Long userId){
        //Get the user first
        User user = userRepositories.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("User does not exist")
                );

        //Check the role
        if(user.getRole() != null){
            if(user.getRole() == Roles.RUNNER){
                throw new RunnerProfileAlreadyExistsException("User is already registered as Runner");
            }

            else{
                throw new InvalidRoleException("User is already assigned with other role.");
            }
        }
        if(runnerProfileRepository.existsByUser_Id(userId)){
            throw new RunnerProfileAlreadyExistsException("Runner already exists");
        }

        //Set user role first
        user.setRole(Roles.RUNNER);
        userRepositories.save(user);

        RunnerProfile runnerProfile = new RunnerProfile();
        runnerProfile.setUser(user);
        runnerProfile.setOnline(true);
        runnerProfile.setTotalDeliveries(0L);
        runnerProfile.setRatingCount(0L);
        runnerProfile.setRatingSum(0L);

        runnerProfileRepository.save(runnerProfile);

        return mapToRunner(runnerProfile);
    }

    public RunnerResponseDTO getById(Long id){
        RunnerProfile runnerProfile = runnerProfileRepository.findById(id)
                .orElseThrow(() ->  new RunnerProfileNotFoundException("Runner profile not found")
                );
        return mapToRunner(runnerProfile);
    }

    public RunnerResponseDTO getByUserId(Long userId){
        RunnerProfile runnerProfile = runnerProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RunnerProfileNotFoundException("Runner profile not found for given user"));
        return mapToRunner(runnerProfile);
    }

    public RunnerResponseDTO mapToRunner(RunnerProfile runnerProfile){
        RunnerResponseDTO responseDTO = new RunnerResponseDTO();
        responseDTO.setName(runnerProfile.getUser().getName());
        responseDTO.setContact(runnerProfile.getUser().getContact());
        responseDTO.setTotalDeliveries(runnerProfile.getTotalDeliveries());
        String avg_rating = String.format("%.2f",
                (double) runnerProfile.getRatingSum() / runnerProfile.getRatingCount());
        responseDTO.setRating(avg_rating);
        responseDTO.setOnline(runnerProfile.isOnline());

        return responseDTO;
    }
}
