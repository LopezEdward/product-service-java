package dev.edwlopez.microservices.storage_service.service.impl;

import dev.edwlopez.microservices.storage_service.entity.Product;
import dev.edwlopez.microservices.storage_service.repository.ProductRepository;
import dev.edwlopez.microservices.storage_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ImplProductService implements ProductService {
    @Autowired
    private ProductRepository repo;

    @Override
    public Stream<Product> getAll() {
        return repo.findAll().stream();
    }

    @Override
    public Optional<Product> searchById(Long aLong) {
        return repo.findById(aLong);
    }

    @Override
    public Product create(Product entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Product entity) {
        repo.delete(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        repo.deleteById(aLong);
    }

    @Override
    public Product update(Product entity) {
        return repo.save(entity);
    }
}
