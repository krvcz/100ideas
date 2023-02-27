package pl.sebastian.ideas100.category.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.repository.CategoryRepository;


import java.util.*;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Category> getCategory(UUID id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public Category updateCategory(UUID id, Category category) {
        Category oldCategory = categoryRepository.getById(id);
        oldCategory.setName(category.getName());

        return categoryRepository.save(oldCategory);

    }

    @Transactional
    public void removeCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category addCategory(Category category) {
        Category newCategory  = new Category();
        newCategory.setName(category.getName());
        return categoryRepository.save(newCategory);
    }
}
