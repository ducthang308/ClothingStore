package com.example.demo.Services;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ProductImageDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Categories;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductImages;
import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.ProductImagesRepository;
import com.example.demo.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductImagesRepository productImagesRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Categories existingCategories = categoriesRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()->
                        new DataNotFoundException("Cannot find categories with id: "+productDTO.getCategoryId()));
        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .color(productDTO.getColor())
                .categories(existingCategories).build();
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(long productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.getDetailProduct(productId);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with ID: "+productId);
    }

    @Override
    public List<Product> getAllProducts(String keyword, Long categoryId) {
        return productRepository.searchProducts(categoryId, keyword);
    }

    @Override
    public Product updateProduct(long productId, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(productId);
        if(existingProduct != null){
            Categories existingCategories = categoriesRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(()-> new DataNotFoundException("Cannot find category with id: "+productDTO.getCategoryId()));
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setCategories(existingCategories);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setColor(productDTO.getColor());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByProductName(name);
    }

    @Override
    public ProductImages createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(()->
                        new DataNotFoundException("Cannot find product with id: "+productImageDTO.getProductId()));
        ProductImages newProductImage = ProductImages.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        int size = productImagesRepository.findByProductId(productId).size();
        if(size >= ProductImages.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new DataNotFoundException("Numbers of images must be <= "+ ProductImages.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImagesRepository.save(newProductImage);
    }

    @Override
    public List<Product> findProductsByIds(List<Long> productIds) {
        return productRepository.findProductsByIds(productIds);
    }

    @Override
    public ProductImages getUrl(String imageName) {
        return productImagesRepository.findByImageUrl(imageName);
    }
}
