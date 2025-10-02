package dev.edwlopez.microservices.storage_service;

import dev.edwlopez.microservices.storage_service.service.ProductService;
import dev.edwlopez.microservices.storage_service.service.impl.ImplProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageServiceApplication.class, args);
	}

    @Bean
    public ProductService productService () {
        return new ImplProductService();
    }
}
