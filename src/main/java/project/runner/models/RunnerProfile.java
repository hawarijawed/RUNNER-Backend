package project.runner.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "runner_profiles")
public class RunnerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(nullable = false)
    private boolean online = false;

    @Column(nullable = false)
    private Long totalDeliveries = 0L;

    @Column(nullable = false)
    private Long ratingCount = 0L;

    @Column(nullable = false)
    private Long ratingSum = 0L;

    private Date createdAt;
}
