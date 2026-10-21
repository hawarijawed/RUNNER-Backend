package project.runner.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "delivery_id",
            unique = true,
            nullable = false
    )
    private Delivery delivery;
    @Column(nullable = false)
    @Min(0)
    @Max(5)
    private Integer rating;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime ratedAt;
}
