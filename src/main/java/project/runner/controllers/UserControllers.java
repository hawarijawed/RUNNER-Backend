package project.runner.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import project.runner.DTOs.CreateUserDTO;
import project.runner.DTOs.UserResponse;
import project.runner.services.UserServices;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControllers {
    private final UserServices userServices;

    public UserControllers(UserServices userServices){
        this.userServices = userServices;
    }

    @GetMapping("/")
    public List<UserResponse> getAll(){
        return userServices.getAll();
    }

    @GetMapping("/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email){
        return userServices.findByEmail(email);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return userServices.findById(id);
    }

    @PostMapping("/")
    public UserResponse addNewUser(@RequestBody @Valid CreateUserDTO createUserDTO){
        return userServices.createNewUser(createUserDTO);
    }


}
