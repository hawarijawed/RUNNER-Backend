package project.runner.services;

import org.springframework.stereotype.Service;
import project.runner.repositories.UserRepositories;

@Service
public class UserServices {

    private final UserRepositories userRepositories;

    public UserServices(UserRepositories userRepositories){
        this.userRepositories = userRepositories;
    }

    //Create new user
}
