package kakeibo.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getType()
                ))
                .toList();
    }

    public List<CategoryDto> getCategoriesByType(CategoryType type) {
        return categoryRepository.findByType(type)
                .stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getType()
                ))
                .toList();
    }
}