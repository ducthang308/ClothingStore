package com.example.demo.Controllers;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductImages;
import com.example.demo.Services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import com.example.demo.DTO.ProductImageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO,
                                           BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId
    ) {
        List<Product> products = productService.getAllProducts(keyword, categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllProductById(@PathVariable("id") Long productId)
    {
        try {
            Product existingId = productService.getProductById(productId);
            return ResponseEntity.ok(existingId);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,
                                           @Valid @RequestBody ProductDTO productDTO)
    {
        try {
            productService.updateProduct(id, productDTO);
            return ResponseEntity.ok("Update successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id)
    {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Delete successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-ids")
    public ResponseEntity<?> getProdutcByIds(@RequestParam("ids") String ids){
        try{
            List<Long> productIds = Arrays.stream(ids.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Product> products = productService.findProductsByIds(productIds);
            return ResponseEntity.ok(products);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try{
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);
            }else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpg").toUri()));
            }
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @RequestParam("files") List<MultipartFile> files,
            @PathVariable("id") Long productId) {
        if (files == null || files.isEmpty()) {
            logger.warn("No files provided for upload.");
            return ResponseEntity.badRequest().body("No files provided for upload.");
        }
        try {
            logger.info("Start uploading images for product ID: " + productId);

            Product existingProduct = productService.getProductById(productId);
            logger.info("Product found: " + existingProduct.getProductName());

            // Kiểm tra nếu danh sách files null hoặc rỗng
            if (files == null || files.size() == 0) {
                logger.warn("No files provided for upload.");
                return ResponseEntity.badRequest().body("No files provided for upload.");
            }

            // Kiểm tra số lượng file
            if (files.size() > ProductImages.MAXIMUM_IMAGES_PER_PRODUCT) {
                logger.warn("Too many files uploaded. Maximum allowed is 5.");
                return ResponseEntity.badRequest().body("Maximum allowed is 5 images.");
            }

            List<ProductImages> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                logger.info("Processing file: " + file.getOriginalFilename());

                // Bỏ qua các file rỗng
                if (file.isEmpty()) {
                    logger.warn("Skipped empty file: " + file.getOriginalFilename());
                    continue;
                }

                // Kiểm tra kích thước file
                if (file.getSize() > 10 * 1024 * 1024) {
                    logger.warn("File too large: " + file.getOriginalFilename());
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large. Maximum allowed size is 10MB.");
                }

                // Kiểm tra loại file
                if (!isImageFile(file)) {
                    logger.warn("Unsupported media type for file: " + file.getOriginalFilename());
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Unsupported media type. Please upload an image file.");
                }

                try {
                    // Lưu file
                    String filename = storeFile(file);
                    logger.info("File stored successfully: " + filename);

                    // Tạo đối tượng ProductImage và thêm vào danh sách
                    ProductImages productImage = productService.creatProductImage(
                            existingProduct.getId(),
                            ProductImageDTO.builder().imageUrl(filename).build()
                    );
                    productImages.add(productImage);

                } catch (IOException ex) {
                    logger.error("Error storing file: " + file.getOriginalFilename(), ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("An error occurred while storing the file: " + file.getOriginalFilename());
                }
            }

            logger.info("Successfully uploaded all images for product ID: " + productId);
            return ResponseEntity.ok(productImages);

        } catch (NoSuchElementException ex) {
            logger.error("Product not found for ID: " + productId, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        } catch (Exception e) {
            logger.error("Unexpected error during image upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while uploading images.");
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            logger.error("Invalid image file format or missing file name: " + file.getOriginalFilename());
            throw new IOException("Invalid image file format or missing file name.");
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        logger.info("Storing file with unique name: " + uniqueFilename);

        // Đường dẫn đến thư mục lưu trữ
        java.nio.file.Path uploadDir = Paths.get("uploads");

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if (!Files.exists(uploadDir)) {
            logger.info("Upload directory does not exist, creating directory: " + uploadDir.toString());
            Files.createDirectories(uploadDir);
        }

        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = uploadDir.resolve(uniqueFilename);

        logger.info("Saving file to destination: " + destination.toString());

        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        logger.info("File successfully stored: " + uniqueFilename);

        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

}
