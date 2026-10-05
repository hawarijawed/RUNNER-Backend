package project.runner.services;

import org.springframework.stereotype.Service;
import project.runner.DTOs.CreateUserDTO;
import project.runner.DTOs.UserResponse;
import project.runner.exceptions.EmailAlreadyExistsException;
import project.runner.exceptions.UserNotFoundException;
import project.runner.models.User;
import project.runner.repositories.UserRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private final UserRepositories userRepositories;

    public UserServices(UserRepositories userRepositories){
        this.userRepositories = userRepositories;
    }

    //Create new user
    public UserResponse createNewUser(CreateUserDTO createUserDTO){
        if(userRepositories.existsByEmail(createUserDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email ID already exists in database");
        }

        User user = new User();

        user.setName(createUserDTO.getName());
        user.setEmail(createUserDTO.getEmail());
        user.setContact(createUserDTO.getContact());
        user.setPasswordHash(createUserDTO.getPassword()); //THIS LINE DESERVE ATTENTION LATER
        user.setRole(createUserDTO.getRole());

        User savedUser = userRepositories.save(user);

        return mapToUser(savedUser);
    }

    public UserResponse findById(Long id){
        User user = userRepositories.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return mapToUser(user);
    }

    public UserResponse findByEmail(String email){
        User user = userRepositories.findByEmail(email).orElseThrow(
                ()-> new UserNotFoundException("User does not exist with this email")
        );

        return mapToUser(user);
    }

    public List<UserResponse> getAll(){
        List<User> users = userRepositories.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : users) {
            userResponseList.add(mapToUser(user));
        }

        return userResponseList;
    }

    public UserResponse mapToUser(User user){

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setContact(user.getContact());
        userResponse.setRole(user.getRole());

        return userResponse;
    }
}
