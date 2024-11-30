package com.example.demo.Services;

import com.example.demo.DTO.CartsDTO;
import com.example.demo.DTO.CategoriesDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Carts;
import com.example.demo.Models.Categories;

import java.util.List;

public interface ICartsService {
    Carts createCart(CartsDTO cartsDTO) throws DataNotFoundException;
    Long getCartByUserId(long userId);
    List<Carts> getAllCarts();
    Carts updateCart(long id) throws DataNotFoundException;
    void deleteCart(long id);
}
