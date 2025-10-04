package dev.edwlopez.microservices.storage_service.dto.product;

import com.fasterxml.jackson.annotation.JsonAlias;
import dev.edwlopez.microservices.storage_service.dto.ProductDTO;
import dev.edwlopez.microservices.storage_service.entity.Product;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PutProductDTO {
    @JsonAlias("id_pro")
    private Long id;
    @Size(min = 4, max = 100)
    @JsonAlias("nom_pro")
    private String name;
    @DecimalMin("0.10")
    @DecimalMax("999999.99")
    @JsonAlias("pre_pro")
    private Double price;
    @JsonAlias("id_uni")
    private Long unitId;
    @JsonAlias("id_marca")
    private Long marcaId;
    @JsonAlias("id_cat")
    private Long categoryId;
    @DecimalMin("0.10")
    @DecimalMax("999999.99")
    @JsonAlias("stk_pro")
    private Double stock;
    @Pattern(regexp = "^[ynYN]$")
    @JsonAlias("estado")
    @Size(min = 1, max = 1)
    private String state = "Y";

    public static PutProductDTO fromEntity (Product product) {
        var dto = new PutProductDTO();

        dto.id = product.getId();
        dto.name = product.getName();
        dto.stock = product.getStock();
        dto.price = product.getPrice();
        dto.unitId = product.getUnitId();
        dto.marcaId = product.getMarcaId();
        dto.categoryId = product.getCategoryId();
        dto.state = product.getState();

        return dto;
    }

    public ProductDTO toMainDTO () {
        var dto = new ProductDTO();

        dto.setName(this.name);
        dto.setStock(this.stock);
        dto.setId(this.id);
        dto.setPrice(this.price);
        dto.setUnitId(this.unitId);
        dto.setMarcaId(this.marcaId);
        dto.setCategoryId(this.categoryId);
        dto.setState(this.state);

        return dto;
    }

    public Product toEntity () {
        var entity = new Product();

        entity.setId(this.id);
        entity.setName(this.name);
        entity.setStock(this.stock);
        entity.setPrice(this.price);
        entity.setUnitId(this.unitId);
        entity.setMarcaId(this.marcaId);
        entity.setCategoryId(this.categoryId);
        entity.setState(this.state);

        return entity;
    }
}
