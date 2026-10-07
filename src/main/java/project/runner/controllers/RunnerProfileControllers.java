package project.runner.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.runner.DTOs.RunnerProfileResponse;
import project.runner.models.RunnerProfile;
import project.runner.services.RunnerProfileServices;

@RestController
@RequestMapping("/runner-profile")
@AllArgsConstructor
public class RunnerProfileControllers {
    private final RunnerProfileServices runnerProfileServices;

    @PostMapping("/{userId}")
    public RunnerProfileResponse createRunnerProfile(@PathVariable Long userId){
        return runnerProfileServices.createRunnerProfile(userId);
    }

    @GetMapping("/user/{userId}")
    public RunnerProfile getRunnerByUserId(@PathVariable Long userId){
        return runnerProfileServices.getByUserId(userId);
    }

    @GetMapping("/{id}")
    public RunnerProfile getRunnerById(@PathVariable Long id){
        return runnerProfileServices.getById(id);
    }
}
