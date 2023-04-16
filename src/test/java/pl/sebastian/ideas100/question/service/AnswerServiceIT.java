package pl.sebastian.ideas100.question.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.repository.CategoryRepository;
import pl.sebastian.ideas100.question.dto.AnswerStatDto;
import pl.sebastian.ideas100.question.model.Answer;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.repository.AnswerRepository;
import pl.sebastian.ideas100.question.repository.QuestionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
@Transactional
@Rollback
class AnswerServiceIT {

    private static Category category;

    private static Question question;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerStatMapper answerStatMapper;

    @BeforeEach
     void setUp() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        categoryRepository.deleteAll();
        question = new Question("Question11");
        category = new Category("testowa");
        question.setCategory(category);
        categoryRepository.save(category);
        questionRepository.save(question);
    }

    @Test
    void shouldGetAnswers() {
        // Given
        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        Answer answer3 = new Answer("Answer3");

        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        question.addAnswer(answer1);
        question.addAnswer(answer2);
        question.addAnswer(answer3);



        // When
        List<AnswerStatDto> answerList = answerService.getAnswers();

        // Then
        assertThat(answerList).hasSize(3)
                .extracting(AnswerStatDto::getContent)
                .containsExactlyInAnyOrder("Answer1", "Answer2", "Answer3");

    }

    @Test
    void shouldGetAnswersByQuestionId() {
        // Given
        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        Answer answer3 = new Answer("Answer3");
        question.addAnswer(answer1);
        question.addAnswer(answer2);
        question.addAnswer(answer3);

        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        // When
        List<AnswerStatDto> answerList = answerService.getAnswersByQuestionId(question.getId());

        // Then
        assertThat(answerList).hasSize(3)
                .extracting(AnswerStatDto::getContent)
                .containsExactlyInAnyOrder("Answer1", "Answer2", "Answer3");

    }

    @Test
    void shouldGetAnswerById() {
        // Given
        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        question.addAnswer(answer1);
        question.addAnswer(answer2);

        answerRepository.save(answer1);
        answerRepository.save(answer2);

        // When
        AnswerStatDto foundAnswer1 = answerService.getAnswer(answer1.getId());
        AnswerStatDto foundAnswer2 = answerService.getAnswer(answer2.getId());
        Throwable thrown = catchThrowable(() -> {
            answerService.getAnswer(UUID.randomUUID());
        });

        // Then
        assertThat(foundAnswer1)
                .extracting(AnswerStatDto::getContent)
                .isEqualTo("Answer1");

        assertThat(foundAnswer2)
                .extracting(AnswerStatDto::getContent)
                .isEqualTo("Answer2");

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void shouldUpdateAnswer() {
        // Given
        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        question.addAnswer(answer1);

        answerRepository.save(answer1);

        // When
        answerService.updateAnswer(answer1.getId(), answerStatMapper.map(answer2));
        Optional<Answer> answer = answerRepository.findById(answer1.getId());

        // Then
        assertThat(answer).isPresent()
                .get()
                .extracting(Answer::getContent)
                .isEqualTo("Answer2");


    }

    @Test
    void removeAnswer() {
        // Given
        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        question.addAnswer(answer1);
        question.addAnswer(answer2);

        answerRepository.save(answer1);
        answerRepository.save(answer2);

        // When
        answerService.removeAnswer(answer2.getId());
        List<Answer> answers = answerRepository.findAll();

        // Then
        assertThat(answers).hasSize(1)
                .extracting(Answer::getContent)
                .isEqualTo(List.of("Answer1"));
    }

    @Test
    void shouldAddAnswer() {
        // Given
        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        question.addAnswer(answer1);
        answerRepository.save(answer1);

        // When
        answerService.addAnswer(question.getId(), answerStatMapper.map(answer2));
        List<Answer> answers = answerRepository.findAllByQuestionId(question.getId());

        // Then
        assertThat(answers).hasSize(2)
                .extracting(Answer::getContent)
                .containsExactlyInAnyOrder("Answer1", "Answer2");
    }
}
