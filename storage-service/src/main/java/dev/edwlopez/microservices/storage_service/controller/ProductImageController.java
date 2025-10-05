package dev.edwlopez.microservices.storage_service.controller;

import dev.edwlopez.microservices.storage_service.entity.ProductImage;
import dev.edwlopez.microservices.storage_service.service.ProductImageService;
import dev.edwlopez.microservices.storage_service.service.aws.S3Service;
import dev.edwlopez.microservices.storage_service.service.impl.ImplS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1/products/image")
public class ProductImageController {
    @Autowired
    private ProductImageService imgService;

    @Value("${spring.destination.folder}")
    private String destinationFolder;

    @Value("${aws.bucket.defaultTarget}")
    private String defaultBucket;

    @Autowired
    private S3Service s3Service;

    @PostMapping()
    public ResponseEntity<?> uploadProductImage (
            @RequestParam("pId") Long productId,
            @RequestParam String key,
            @RequestPart MultipartFile file) throws IOException {
        try {
            var or = file.getOriginalFilename();
            key = (or.contains(".")) ? key + or.substring(or.lastIndexOf(".")) : key;
            var map = new HashMap<Object, Object>(3);
            map.put("productId", productId);
            map.put("key", key);

            var a = (ImplS3Service) s3Service;
            boolean res = a.uploadFile(this.defaultBucket, key, file.getBytes());

            if (res) {
                var entity = new ProductImage();
                entity.setKey(key);
                entity.setProductId(productId);
                imgService.create(entity);

                map.put("result", "CREATED");

                return ResponseEntity.ok(map);
            }

            map.put("result", "ERROR");

            return ResponseEntity.internalServerError().body(map);
        } catch (IOException ex) {
            throw new IOException("Error al procesar el archivo", ex);
        }
    }

    /*@GetMapping("/image")
    public ResponseEntity<?> downloadFile (
            @RequestParam("p_id") Long productId
    ) {
        try {

        } catch (IOException ex) {

        }
    }*/
}
