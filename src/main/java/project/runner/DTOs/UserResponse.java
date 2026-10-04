package project.runner.DTOs;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import project.runner.models.enumerates.Roles;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String contact;
    @Enumerated(EnumType.STRING)
    private Roles role;
}