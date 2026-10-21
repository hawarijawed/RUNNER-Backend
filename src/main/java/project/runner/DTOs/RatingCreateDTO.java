package project.runner.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingCreateDTO {
    @NotNull
    @Min(0)
    @Max(5)
    private Integer rating;
}
