package project.runner.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.runner.DTOs.LoginRequestDTO;
import project.runner.DTOs.LoginResponseDTO;
import project.runner.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(userDetails == null){
            throw new AuthenticationServiceException(
                    "Unable to retrieve authenticated user details"
            );
        }
        String token = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(
                token,
                "Bearer",
                jwtService.getTokenExpiration()
        );
    }
}
