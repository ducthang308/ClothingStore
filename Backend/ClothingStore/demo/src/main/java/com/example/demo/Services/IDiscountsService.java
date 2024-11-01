package com.example.demo.Services;

import com.example.demo.DTO.DiscountsDTO;
import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.Models.Discounts;

import java.util.List;

public interface IDiscountsService {
    Discounts createDiscount(DiscountsDTO discountsDTO) throws DataNotFoundException;
    Discounts updateDiscount(long id, DiscountsDTO discountsDTO) throws DataNotFoundException;
    void deleteDiscount(long id);
    List<Discounts> getAllDiscounts();
}
