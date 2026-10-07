package project.runner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.runner.models.RunnerProfile;

import java.util.Optional;
@Repository
public interface RunnerProfileRepository extends JpaRepository<RunnerProfile, Long> {
    Optional<RunnerProfile> findByUserId(Long id);
    boolean existsByUserId(Long id);
}
