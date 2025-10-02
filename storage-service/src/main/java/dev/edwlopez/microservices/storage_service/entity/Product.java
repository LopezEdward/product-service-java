package dev.edwlopez.microservices.storage_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
    @Id
    @Column(name = "id_pro")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_pro", length = 200, nullable = false, unique = true)
    private String name;

    @Column(name = "pre_pro", nullable = false)
    private Double price;

    @Column(name = "id_uni")
    private Long unitId;

    @Column(name = "id_marca")
    private Long marcaId;

    @Column(name = "id_cat")
    private Long categoryId;

    @Column(name = "stk_pro", nullable = false)
    private Double stock;

    @Column(name = "estado")
    private Character state = 'Y';
}
