package com.example.demo.Services;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ProductImageDTO;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductImages;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long productId) throws Exception;
    List<ProductDTO> getAllProducts(String keyword, Long categoryId);
    Product updateProduct(long productId, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImages createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
    List<Product> findProductsByIds(List<Long> productIds);
    ProductImages getUrl(String imageName);
    //    List<ProductImages> getImageUrlByProductId(Long productId);
    List<String> getImageUrlsByProductId(Long productId);
}