package pl.sebastian.ideas100.category.service;

import org.springframework.stereotype.Component;
import pl.sebastian.ideas100.category.dto.CategoryDTO;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.question.model.Question;

@Component
public class CategoryWithStatsMapper {

    public CategoryDTO map(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());


        categoryDTO.setQuestions(category.getQuestions().size());
        categoryDTO.setAnswers(category.getQuestions().stream()
                .map(Question::getAnswers).count());

        return categoryDTO;
    }

    public Category map(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());


        return category;
    }



}
