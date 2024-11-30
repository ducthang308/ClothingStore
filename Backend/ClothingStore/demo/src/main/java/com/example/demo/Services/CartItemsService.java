package com.example.demo.Services;

import com.example.demo.DTO.CartItemQuantityDTO;
import com.example.demo.DTO.CartItemsDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.CartItems;
import com.example.demo.Models.Carts;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductImages;
import com.example.demo.Repository.CartItemsRepository;
import com.example.demo.Repository.CartsRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemsService implements ICartItemsService{
    private final CartsRepository cartsRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;
    private final UsersRepository usersRepository;

    @Override
    public CartItems createCartItem(CartItemsDTO cartItemsDTO) throws DataNotFoundException {
        Carts carts = cartsRepository.findById(cartItemsDTO.getCartId())
                .orElseThrow(()->new DataNotFoundException("Not found cartId: "+cartItemsDTO.getCartId()));
        Product product = productRepository.findById(cartItemsDTO.getProductId())
                .orElseThrow(()->new DataNotFoundException("Not found cartId: "+cartItemsDTO.getProductId()));
        CartItems cartItems = CartItems.builder()
                .carts(carts)
                .product(product)
                .quantity(cartItemsDTO.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return cartItemsRepository.save(cartItems);
    }

    @Override
    public List<CartItemsDTO> getAllCartItems() {
        List<CartItems> cartItemsList = cartItemsRepository.findAll();

        // Chuyển đổi từ List<CartItems> sang List<CartItemsDTO>
        List<CartItemsDTO> cartItemsDTOList = cartItemsList.stream()
                .map(cartItem -> {
                    CartItemsDTO dto = new CartItemsDTO();
                    dto.setId(cartItem.getId());
                    dto.setProductId(cartItem.getProduct().getId());
                    dto.setCartId(cartItem.getCarts().getId());
                    dto.setQuantity(cartItem.getQuantity());
                    dto.setCreatedAt(cartItem.getCreatedAt());
                    dto.setUpdatedAt(cartItem.getUpdatedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        return cartItemsDTOList;
    }


    @Override
    public void updateCartItem(Long id, CartItemQuantityDTO cartItemQuantityDTO) throws DataNotFoundException {
        Optional<CartItems> cartItemOptional = cartItemsRepository.findById(id);

        if (!cartItemOptional.isPresent()) {
            throw new DataNotFoundException("Cart item not found with id " + id);
        }
        CartItems cartItem = cartItemOptional.get();
        cartItem.setQuantity(cartItemQuantityDTO.getQuantity());
        cartItem.setUpdatedAt(LocalDateTime.now());
        cartItemsRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartId, Long productId) {
        cartItemsRepository.deleteByCarts_IdAndProduct_Id(cartId, productId);
    }

    @Override
    public List<CartItemsDTO> getAllCartItemsByCartId(Long cartId) {
        List<CartItems> cartItemsList = cartItemsRepository.findByCarts_Id(cartId);

        List<CartItemsDTO> cartItemsDTOList = cartItemsList.stream()
                .map(cartItem -> {
                    CartItemsDTO dto = new CartItemsDTO();
                    dto.setId(cartItem.getId());
                    dto.setProductId(cartItem.getProduct().getId());
                    dto.setCartId(cartItem.getCarts().getId());
                    dto.setQuantity(cartItem.getQuantity());
                    dto.setProductName(cartItem.getProduct().getProductName());
                    dto.setPrice(cartItem.getProduct().getPrice());
                    dto.setColor(cartItem.getProduct().getColor());

                    List<ProductImages> productImages = cartItem.getProduct().getProductImages();
                    if (productImages != null && !productImages.isEmpty()) {
                        // Giả sử chúng ta lấy ảnh đầu tiên
                        dto.setImageUrl(productImages.get(0).getImageUrl());
                    } else {
                        dto.setImageUrl(null);
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        return cartItemsDTOList;
    }


}
