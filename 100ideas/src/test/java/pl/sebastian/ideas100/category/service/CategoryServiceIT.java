package pl.sebastian.ideas100.category.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.category.dto.CategoryDTO;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.repository.CategoryRepository;
import pl.sebastian.ideas100.question.repository.AnswerRepository;
import pl.sebastian.ideas100.question.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback
class CategoryServiceIT {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryWithStatsMapper categoryWithStatsMapper;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        categoryRepository.deleteAll();

        Category category1 = new Category("Category1");
        Category category2 = new Category("Category2");
        Category category3 = new Category("Category3");
        categoryRepository.saveAll(List.of(category1, category2, category3));


    }

    @Test
    void shouldReturnCategories() {

        // Given
        List<Category> categoriesRepo = categoryRepository.findAll();

        // When
        List<Category> categoryList= categoryService.getCategories(Pageable.unpaged()).getContent();

        // Then
        assertThat(categoryList).hasSize(3)
                .isEqualTo(categoriesRepo)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("Category1", "Category2", "Category3");

    }

    @Test
    void shouldReturnCategoriesByKeyword() {
        // Given
        Category categoryABC = new Category("ABCABC");
        Category category = new Category("Category4");
        categoryRepository.saveAll(List.of(categoryABC, category));

        List<Category> categoriesRepo = categoryRepository.findAllByNameContainingIgnoreCase("ABC", Pageable.unpaged()).getContent();

        // When
        List<Category> categoryList= categoryService.getCategories(Pageable.unpaged(), "ABC").getContent();

        // Then
        assertThat(categoryList).hasSize(1)
                .isEqualTo(categoriesRepo)
                .extracting(Category::getName)
                .containsOnly("ABCABC");
    }

    @Test
    void getCategory() {
        // Given
        Category categoryRepo = categoryRepository.findAllByNameContainingIgnoreCase("Category3", Pageable.unpaged())
                .getContent()
                .get(0);

        // When
        Category category = categoryWithStatsMapper.map(categoryService.getCategory(categoryRepo.getId()));

        // Then
        assertThat(category)
                .isEqualTo(categoryRepo)
                .extracting(Category::getName)
                .isEqualTo("Category3");

    }

    @Test
    void shouldUpdateCategory() {
        // Given
        CategoryDTO category = new CategoryDTO("Category4");
        Category categoryRepo = categoryRepository.findAllByNameContainingIgnoreCase("Category3", Pageable.unpaged())
                .getContent()
                .get(0);

        // When
        Category updatedCategory = categoryWithStatsMapper.map(categoryService.updateCategory(categoryRepo.getId(), category));

        // Then
        assertThat(updatedCategory).extracting(Category::getName)
                .isEqualTo("Category4");

        assertThat(updatedCategory).extracting(Category::getId)
                .isEqualTo(categoryRepo.getId());

    }

    @Test
    void shouldRemoveCategory() {
        // Given
        Category category = new Category("Category4");
        categoryRepository.save(category);
        Category categoryRepo = categoryRepository.findAllByNameContainingIgnoreCase("Category3", Pageable.unpaged())
                .getContent()
                .get(0);

        // When
        categoryService.removeCategory(categoryRepo.getId());


        // Then
        assertThat(categoryRepository.findAll()).hasSize(3)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("Category1", "Category2", "Category4");

        assertThat(categoryRepository.findById(categoryRepo.getId())).isNotPresent();
    }

    @Test
    void addCategory() {
        // Given
        CategoryDTO category = new CategoryDTO("Category4");


        // When
        categoryService.addCategory(category);


        // Then
        assertThat(categoryRepository.findAll()).hasSize(4)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("Category1", "Category2", "Category4", "Category3");

    }
}