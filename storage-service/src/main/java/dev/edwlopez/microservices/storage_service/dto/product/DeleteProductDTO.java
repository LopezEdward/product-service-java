package dev.edwlopez.microservices.storage_service.dto.product;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DeleteProductDTO {
    @NotBlank
    private Long id;
    @Size(min = 4, max = 100)
    private String name;
    @DecimalMin("0.10")
    @DecimalMax("999999.99")
    private Double price;
    private Long unitId;
    private Long marcaId;
    private Long categoryId;
    @DecimalMin("0.10")
    @DecimalMax("999999.99")
    private Double stock;
    @Pattern(regexp = "^[yn]$")
    private Character state = 'Y';
}
