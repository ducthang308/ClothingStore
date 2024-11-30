package com.example.demo.Services;

import com.example.demo.DTO.CartItemQuantityDTO;
import com.example.demo.DTO.CartItemsDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.CartItems;

import java.util.List;

public interface ICartItemsService {
    CartItems createCartItem(CartItemsDTO cartItemsDTO) throws DataNotFoundException;
    //    Carts getCartById(long id);
    List<CartItemsDTO> getAllCartItems();
    void updateCartItem(Long id, CartItemQuantityDTO cartItemQuantityDTO) throws DataNotFoundException;
    void deleteCartItem(Long cartId, Long productId) ;
    List<CartItemsDTO> getAllCartItemsByCartId(Long cartId);
}
