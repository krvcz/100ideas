package pl.sebastian.ideas100.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sebastian.ideas100.category.repository.CategoryRepository;
import pl.sebastian.ideas100.question.dto.QuestionStatDto;
import pl.sebastian.ideas100.question.model.Question;

@Component
@RequiredArgsConstructor
public class QuestionWithStatsMapper {

    private final CategoryRepository categoryRepository;

    public QuestionStatDto map(Question question) {
        QuestionStatDto questionStatDto = new QuestionStatDto();

        questionStatDto.setId(question.getId());
        questionStatDto.setContent(question.getContent());
        questionStatDto.setAnswers(question.getAnswers() != null ? question.getAnswers().size() : 0);
        questionStatDto.setCategory(question.getCategory().getName());
        questionStatDto.setCategoryId(question.getCategory().getId());

        return questionStatDto;
    }

    public Question map(QuestionStatDto questionStatDto) {
        Question question = new Question();

        question.setCategory(categoryRepository.getById(questionStatDto.getCategoryId()));
        question.setContent(questionStatDto.getContent());

        return question;
    }

}
