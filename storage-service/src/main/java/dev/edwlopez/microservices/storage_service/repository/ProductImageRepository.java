package dev.edwlopez.microservices.storage_service.repository;

import dev.edwlopez.microservices.storage_service.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId (Long productId);
}
