package com.example.demo.Services;

import com.example.demo.DTO.CategoriesDTO;
import com.example.demo.Models.Categories;

import java.util.List;

public interface ICategoriesService {
    Categories createCategory(CategoriesDTO categoryDTO);
    Categories getCategoryById(long id);
    List<Categories> getAllCategories();
    Categories updateCategory(long id, CategoriesDTO categoryDTO);
    void deleteCategory(long id);
}
