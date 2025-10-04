package dev.edwlopez.microservices.storage_service.dto.product;

import com.fasterxml.jackson.annotation.JsonAlias;
import dev.edwlopez.microservices.storage_service.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListProductDTO {
    private List<ProductDTO> items;
    @JsonAlias("n_elems")
    private Long numberOfElements;
}
