package project.runner.auth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.runner.DTOs.LoginRequestDTO;
import project.runner.DTOs.LoginResponseDTO;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO responseDTO = authService.login(loginRequestDTO);

        return ResponseEntity.ok(responseDTO);
    }
}
