package kz.iitu.itse1910.variant2issenbayev.controller;

import kz.iitu.itse1910.variant2issenbayev.dto.request.CategoryCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.CategoryUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.CategoryResp;
import kz.iitu.itse1910.variant2issenbayev.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResp> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResp getCategoryById(@PathVariable("id") long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public CategoryResp createCategory(@Valid @RequestBody CategoryCreationReq creationReq) {
        return categoryService.createCategory(creationReq);
    }

    @PutMapping("/{id}")
    public CategoryResp updateCategory(@PathVariable("id") long id, @Valid @RequestBody CategoryUpdateReq updateReq) {
        return categoryService.updateCategory(id, updateReq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
