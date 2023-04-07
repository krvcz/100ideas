package pl.sebastian.ideas100.category.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.category.dto.CategoryDTO;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.repository.CategoryRepository;
import pl.sebastian.ideas100.common.dto.StatisticsDTO;


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
    public CategoryDTO getCategory(UUID id) {
        return categoryWithStatsMapper.map(categoryRepository.getById(id));
    }


    @Transactional
    public CategoryDTO updateCategory(UUID id, CategoryDTO category) {
        Category oldCategory = categoryRepository.getById(id);
        Category  newCategory = categoryWithStatsMapper.map(category);

        oldCategory.setName(newCategory.getName());


        return categoryWithStatsMapper.map(categoryRepository.save(oldCategory));

    }

    @Transactional
    public void removeCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDTO addCategory(CategoryDTO category) {
        Category newCategory  = new Category();
        newCategory.setName(categoryWithStatsMapper.map(category).getName());
        return categoryWithStatsMapper.map(categoryRepository.save(newCategory));
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesWithStats(Pageable pageable) {

        Page<Category> categories =  categoryRepository.findAll(pageable);

        return new PageImpl<>(categories.stream()
                .map(categoryWithStatsMapper::map)
                .collect(Collectors.toList()), pageable, categories.getTotalElements());

    }

    @Transactional(readOnly = true)
    public StatisticsDTO getAllStatistics() {

        return categoryRepository.generateStats();

    }
}
