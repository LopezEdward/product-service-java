package dev.edwlopez.microservices.storage_service.controller;

import com.netflix.discovery.converters.Auto;
import dev.edwlopez.microservices.storage_service.dto.ProductDTO;
import dev.edwlopez.microservices.storage_service.dto.product.ListProductDTO;
import dev.edwlopez.microservices.storage_service.dto.product.PostProductDTO;
import dev.edwlopez.microservices.storage_service.dto.product.PutProductDTO;
import dev.edwlopez.microservices.storage_service.entity.Product;
import dev.edwlopez.microservices.storage_service.entity.ProductImage;
import dev.edwlopez.microservices.storage_service.service.ProductImageService;
import dev.edwlopez.microservices.storage_service.service.ProductService;
import dev.edwlopez.microservices.storage_service.service.aws.S3Service;
import dev.edwlopez.microservices.storage_service.service.impl.ImplS3Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController()
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ListProductDTO getAll () {
        var dto = new ListProductDTO();
        var elements = service.getAll().map(ProductDTO::fromEntity).toList();

        dto.setItems(elements);
        dto.setNumberOfElements(Long.valueOf(String.valueOf(elements.size())));

        return dto;
    }

    @GetMapping("/{id}")
    public ProductDTO getById (@PathVariable Long id) {
        var data = service.searchById(id).get();

        return ProductDTO.fromEntity(data);
    }

    @PostMapping
    public ProductDTO createProduct (@Valid @RequestBody PostProductDTO dto) {
        var res = service.create(dto.toEntity());

        return ProductDTO.fromEntity(res);
    }

    @PutMapping()
    public ProductDTO updateProduct (@Valid @RequestBody PutProductDTO dto) {
        var entity = dto.toEntity();

        var res = service.update(entity);

        return ProductDTO.fromEntity(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable Long id) {
        final Map<Object, Object> map = new HashMap<>(2);
        map.put("register_deleted_id", id);

        service.deleteById(id);

        map.put("result", "DELETED");

        return ResponseEntity.ok(map);
    }
}
