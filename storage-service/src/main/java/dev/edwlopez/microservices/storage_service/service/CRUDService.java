package dev.edwlopez.microservices.storage_service.service;

import java.util.Optional;
import java.util.stream.Stream;

public interface CRUDService<T, ID>  {
    Stream<T> getAll();
    Optional<T> searchById (ID id);
    T create (T entity);
    void delete(T entity);
    void deleteById(ID id);
}
