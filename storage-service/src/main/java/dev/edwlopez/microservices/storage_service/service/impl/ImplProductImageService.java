package dev.edwlopez.microservices.storage_service.service.impl;

import dev.edwlopez.microservices.storage_service.entity.ProductImage;
import dev.edwlopez.microservices.storage_service.repository.ProductImageRepository;
import dev.edwlopez.microservices.storage_service.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ImplProductImageService implements ProductImageService {
    @Autowired
    private ProductImageRepository repo;

    @Override
    public Stream<ProductImage> getAll() {
        return repo.findAll().stream();
    }

    @Override
    public Optional<ProductImage> searchById(Long aLong) {
        return repo.findById(aLong);
    }

    @Override
    public ProductImage create(ProductImage entity) {
        entity.setId(null);

        return repo.save(entity);
    }

    @Override
    public void delete(ProductImage entity) {
        repo.delete(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        repo.deleteById(aLong);
    }

    @Override
    public ProductImage update(ProductImage entity) {
        return repo.save(entity);
    }

    @Override
    public List<ProductImage> searchByProductId(Long id) {
        return repo.findByProductId(id);
    }
}
