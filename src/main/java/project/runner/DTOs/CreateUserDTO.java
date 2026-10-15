package project.runner.DTOs;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;
import project.runner.models.enumerates.Roles;

@Data
public class CreateUserDTO {

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 10)
    private String contact;
    @NotBlank
    @NotNull
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
