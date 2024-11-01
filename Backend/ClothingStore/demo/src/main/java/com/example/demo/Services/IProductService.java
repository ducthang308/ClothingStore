package com.example.demo.Services;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ProductImageDTO;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductImages;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long productId) throws Exception;
    List<Product> getAllProducts(String keyword, Long categoryId);
    Product updateProduct(long productId, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImages createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
    List<Product> findProductsByIds(List<Long> productIds);
    ProductImages getUrl(String imageName);
}
