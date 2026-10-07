package project.runner.DTOs;

import lombok.Data;

@Data
public class RunnerProfileResponse {
    private boolean online;
    private Double rating;
    private Long totalDeliveries;
}
