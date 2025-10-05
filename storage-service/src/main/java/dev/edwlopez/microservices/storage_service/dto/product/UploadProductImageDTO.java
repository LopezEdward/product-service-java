package dev.edwlopez.microservices.storage_service.dto.product;

import org.springframework.web.multipart.MultipartFile;

public class UploadProductImageDTO {
    private String key;
    private MultipartFile file;
}
