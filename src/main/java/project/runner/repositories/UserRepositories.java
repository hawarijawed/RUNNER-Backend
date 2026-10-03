package project.runner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.runner.models.User;

import java.util.Optional;

public interface UserRepositories extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
