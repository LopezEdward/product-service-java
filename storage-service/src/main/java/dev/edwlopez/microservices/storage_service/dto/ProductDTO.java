package dev.edwlopez.microservices.storage_service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import dev.edwlopez.microservices.storage_service.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    @JsonAlias("id_pro")
    private Long id;
    @JsonAlias("nom_pro")
    private String name;
    @JsonAlias("pre_pro")
    private Double price;
    @JsonAlias("stk_pro")
    private Double stock;
    @JsonAlias("id_uni")
    private Long unitId;
    @JsonAlias("id_marca")
    private Long marcaId;
    @JsonAlias("id_cat")
    private Long categoryId;
    @JsonAlias("estado")
    private Character state = 'Y';

    public static ProductDTO fromEntity (Product product) {
        var dto = new ProductDTO();

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
