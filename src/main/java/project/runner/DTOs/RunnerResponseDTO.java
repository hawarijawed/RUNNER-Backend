package project.runner.DTOs;

import lombok.Data;

@Data
public class RunnerResponseDTO {
    private String name;
    private String contact;
    private boolean online;
    private String rating;
    private Long totalDeliveries;
}
