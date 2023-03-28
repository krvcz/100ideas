package pl.sebastian.ideas100.category.service;

import org.springframework.stereotype.Component;
import pl.sebastian.ideas100.category.dto.CategoryStatDto;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.question.model.Question;

@Component
public class CategoryWithStatsMapper {

    public CategoryStatDto map(Category category) {
        CategoryStatDto categoryStatDto = new CategoryStatDto();

        categoryStatDto.setId(category.getId());
        categoryStatDto.setName(category.getName());
        categoryStatDto.setQuestions(category.getQuestions().size());
        categoryStatDto.setAnswers(category.getQuestions().stream()
                .map(Question::getAnswers).count());

        return categoryStatDto;
    }



}
