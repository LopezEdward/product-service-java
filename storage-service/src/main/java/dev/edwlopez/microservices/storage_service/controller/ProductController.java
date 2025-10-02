package dev.edwlopez.microservices.storage_service.controller;

import dev.edwlopez.microservices.storage_service.dto.ProductDTO;
import dev.edwlopez.microservices.storage_service.entity.Product;
import dev.edwlopez.microservices.storage_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController()
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public Stream<ProductDTO> getAll () {
        return service.getAll().map(ProductDTO::fromEntity);
    }

    @PostMapping
    public ProductDTO createProduct (@RequestBody ProductDTO dto) {
        var res = service.create(dto.toEntity());

        return ProductDTO.fromEntity(res);
    }
}
