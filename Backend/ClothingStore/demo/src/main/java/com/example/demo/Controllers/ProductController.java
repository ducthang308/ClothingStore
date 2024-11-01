package com.example.demo.Controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.Configurations.CloudinaryConfig;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final Cloudinary cloudinary;

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
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> viewImage(@PathVariable ProductImages productImages) {
        try {
            // Tìm kiếm URL của ảnh trong cơ sở dữ liệu dựa trên tên ảnh
            ProductImages imageUrl = productService.getUrl(productImages.getImageUrl());

            if (imageUrl != null) {
                // Trả về URL của ảnh đã được lưu trên Cloudinary
                return ResponseEntity.status(HttpStatus.FOUND).header("Location").build();
            } else {
                return ResponseEntity.status(HttpStatus.FOUND).header("Not found location image").build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving image.");
        }
    }


    @PostMapping(value = "uploads/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> uploadImages(@RequestParam("files") List<MultipartFile> files,
                                          @PathVariable("id") Long productId) {
        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest().body("No files provided for upload.");
        }
        try {
            Product existingProduct = productService.getProductById(productId);
            List<ProductImages> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                // Upload file lên Cloudinary
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = uploadResult.get("url").toString();

                ProductImages productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder().imageUrl(imageUrl).build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok(productImages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while uploading images.");
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
