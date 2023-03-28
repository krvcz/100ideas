package pl.sebastian.ideas100.category.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.category.dto.CategoryStatDto;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.repository.CategoryRepository;



import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryWithStatsMapper categoryWithStatsMapper;


    @Transactional(readOnly = true)
    public Page<Category> getCategories(Pageable pageable) {
        return getCategories(pageable, null);
    }

    @Transactional(readOnly = true)
    public Page<Category> getCategories(Pageable pageable, String search) {

        if  (search == null) {
            return categoryRepository.findAll(pageable);
        } else {
            return categoryRepository.findAllByNameContainingIgnoreCase(search, pageable);
        }
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

    @Transactional(readOnly = true)
    public Page<CategoryStatDto> getCategoriesWithStats(Pageable pageable) {

        Page<Category> categories =  categoryRepository.findAll(pageable);

        return new PageImpl<>(categories.stream()
                .map(categoryWithStatsMapper::map)
                .collect(Collectors.toList()), pageable, categories.getTotalElements());

    }
}
