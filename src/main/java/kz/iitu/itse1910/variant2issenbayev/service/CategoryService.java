package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.request.CategoryCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.CategoryUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.CategoryResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Category;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordAlreadyExistsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordUndeletableException;
import kz.iitu.itse1910.variant2issenbayev.mapper.CategoryMapper;
import kz.iitu.itse1910.variant2issenbayev.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResp> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public CategoryResp getCategoryById(long id) {
        Category category = getByIdOrThrow(id);
        return CategoryMapper.INSTANCE.toDto(category);
    }

    public CategoryResp createCategory(CategoryCreationReq creationReq) {
        throwIfAlreadyTaken(creationReq.getName());
        Category category = CategoryMapper.INSTANCE.toEntity(creationReq);
        return saveCategoryAndMapToDto(category);
    }

    public CategoryResp updateCategory(long id, CategoryUpdateReq updateReq) {
        Category category = getByIdOrThrow(id);

        String newCategoryName = updateReq.getName();
        if (!category.getName().equals(newCategoryName)) {
            throwIfAlreadyTaken(newCategoryName);
        }

        CategoryMapper.INSTANCE.updateEntityFromDto(category, updateReq);
        return saveCategoryAndMapToDto(category);
    }

    public void deleteCategory(long id) {
        Category category = getByIdOrThrow(id);

        if (category.hasAssociatedProducts()) {
            throw new RecordUndeletableException("Category " + category.getName() + " cannot be deleted " +
                    "since there are products associated with it");
        }

        categoryRepository.delete(category);
    }

    Category getByIdOrThrow(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category with id " + id + " does not exist"));
    }

    private void throwIfAlreadyTaken(String categoryName) {
        if (categoryRepository.findByName(categoryName).isPresent()) {
            throw new RecordAlreadyExistsException("Category " + categoryName + " already exists");
        }
    }

    private CategoryResp saveCategoryAndMapToDto(Category category) {
        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toDto(category);
    }
}
