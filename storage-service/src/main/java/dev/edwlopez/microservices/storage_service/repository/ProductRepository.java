package dev.edwlopez.microservices.storage_service.repository;

import dev.edwlopez.microservices.storage_service.entity.Product;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.StoredProcedureParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
