package com.example.demo.Services;

import com.example.demo.DTO.CategoriesDTO;
import com.example.demo.Models.Categories;
import com.example.demo.Repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesService implements ICategoriesService{

    private final CategoriesRepository categoriesRepository;

    @Override
    public Categories createCategory(CategoriesDTO categoryDTO) {
        Categories categories = Categories.builder().categoryName(categoryDTO.getCategoryName()).build();
        return categoriesRepository.save(categories);
    }

    @Override
    public Categories getCategoryById(long id) {
        return categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories updateCategory(long id, CategoriesDTO categoryDTO) {
        Categories existingCategory = getCategoryById(id);
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        categoriesRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    public void deleteCategory(long id) {
        categoriesRepository.deleteById(id);
    }
}
