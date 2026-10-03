package project.runner.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seller_profiles")
public class SellerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private User users;
    private String shop_name;
    private String city;
    private Double longitude; // Handled by frontend map
    private Double latitude;  // Handled by frontend map
}
