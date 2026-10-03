package project.runner.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloClass {
    @GetMapping("/hello")
    public String greeting(){
        return "Hello from Spring";
    }
}
