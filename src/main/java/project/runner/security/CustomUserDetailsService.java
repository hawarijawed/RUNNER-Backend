package project.runner.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.runner.exceptions.UserNotFoundException;
import project.runner.models.User;
import project.runner.repositories.UserRepositories;
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepositories userRepositories;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepositories.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with given email: " + email )
        );

        if(user.getRole() == null){
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .roles("USER")
                    .password(user.getPasswordHash())
                    .build();
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .roles(user.getRole().name())
                .password(user.getPasswordHash())
                .build();

    }
}
