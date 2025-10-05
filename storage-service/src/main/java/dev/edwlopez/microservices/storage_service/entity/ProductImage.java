package dev.edwlopez.microservices.storage_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "product_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "file_key_id", length = 400)
    private String key;
}
