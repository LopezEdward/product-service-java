package dev.edwlopez.microservices.storage_service.service;

import dev.edwlopez.microservices.storage_service.entity.Product;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProductService extends CRUDService<Product, Long> {

}
