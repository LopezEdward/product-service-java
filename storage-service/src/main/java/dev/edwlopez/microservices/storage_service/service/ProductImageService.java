package dev.edwlopez.microservices.storage_service.service;

import dev.edwlopez.microservices.storage_service.entity.ProductImage;

import java.util.List;

public interface ProductImageService extends CRUDService<ProductImage, Long> {
    List<ProductImage> searchByProductId(Long id);
}
