package project.runner.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "delivery_id",
            unique = true,
            nullable = false
    )
    private Delivery delivery;
    @Column(nullable = false)
    private Integer rating;

    private LocalDateTime ratedAt;
}
