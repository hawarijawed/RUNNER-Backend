package project.runner.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.runner.DTOs.RunnerProfileResponse;
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
    public RunnerProfileResponse createRunnerProfile(Long userId){
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
        if(runnerProfileRepository.existsByUserId(userId)){
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

        RunnerProfileResponse response = new RunnerProfileResponse();
        response.setOnline(false); //Runner is online only when he/she ready to receive deliveries
        response.setRating(0.0);
        response.setTotalDeliveries(0L);

        return response;
    }

    public RunnerProfile getById(Long id){
        return runnerProfileRepository.findById(id)
                .orElseThrow(() ->  new RunnerProfileNotFoundException("Runner profile not found")
                );
    }

    public RunnerProfile getByUserId(Long userId){
        return runnerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RunnerProfileNotFoundException("Runner profile not found for given user"));
    }
}
