package com.example.demo.Controllers;

import com.example.demo.DTO.CategoriesDTO;
import com.example.demo.Models.Categories;
import com.example.demo.Services.CategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> createCategories(@Valid @RequestBody CategoriesDTO categoriesDTO, BindingResult result){
        System.out.println("Received category: " + categoriesDTO);
        if(result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoriesService.createCategory(categoriesDTO);
        return ResponseEntity.ok("Create successfully");
    }

    @GetMapping("")
    public ResponseEntity<List<Categories>> getAllCategories(){
        List<Categories> categories = categoriesService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> updateCategories(@PathVariable Long id,
                                              @Valid @RequestBody CategoriesDTO categoriesDTO)
    {
        categoriesService.updateCategory(id, categoriesDTO);
        return ResponseEntity.ok("Update successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<String> deleteCategories(@PathVariable Long id){
        categoriesService.deleteCategory(id);
        return ResponseEntity.ok("Delete successfully");
    }

}
