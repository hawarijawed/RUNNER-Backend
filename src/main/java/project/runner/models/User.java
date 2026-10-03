package project.runner.models;

import jakarta.persistence.*;
import lombok.Data;
import project.runner.models.enumerates.Roles;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private String contact;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
