package pl.sebastian.ideas100.question.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.repository.CategoryRepository;
import pl.sebastian.ideas100.question.model.Answer;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.repository.AnswerRepository;
import pl.sebastian.ideas100.question.repository.QuestionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
@Rollback
class QuestionServiceIT {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private static Category category;

    @BeforeAll
    public static void setUp(){
        category = new Category("testowa");
    }

    @Test
    void shouldGetQuestions() {
        // Given
        questionRepository.deleteAll();

        categoryRepository.save(category);

        Question question1 = new Question("Question11");
        Question question2 = new Question("Question22");
        Question question3 = new Question("Question33");

        question1.setCategory(category);
        question2.setCategory(category);
        question3.setCategory(category);


        // When

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);
        List<Question> questionList = questionService.getQuestions(Pageable.unpaged()).getContent();

        // Then

        assertThat(questionList).hasSize(3)
                .extracting(Question::getContent)
                .containsExactlyInAnyOrder("Question11", "Question22", "Question33");


    }

    @Test
    void shouldAddQuestions() {
        // Given
        questionRepository.deleteAll();

        categoryRepository.save(category);

        Question question1 = new Question("Question11");
        Question question2 = new Question("Question22");
        Question question3 = new Question("Question33");

        question1.setCategory(category);
        question2.setCategory(category);
        question3.setCategory(category);


        // When
        Question testedQuestion1 = questionService.addQuestion(question1);
        Question testedQuestion2 =questionService.addQuestion(question2);
        Question testedQuestion3 =questionService.addQuestion(question3);

        List<Question> questionList = questionRepository.findAll();

        // Then

        assertThat(questionList).hasSize(3)
                .extracting(Question::getContent)
                .containsExactlyInAnyOrder("Question11", "Question22", "Question33");

        assertThat(questionList).extracting(Question::getId)
                .containsOnly(testedQuestion1.getId(), testedQuestion2.getId(), testedQuestion3.getId());

    }


    @Test
    void shouldReturnTwoQuestionsByKeyword() {

        // Given
        questionRepository.deleteAll();
        categoryRepository.save(category);

        Question question1 = new Question("KeywordABAA");
        Question question2 = new Question("KeywordBBB");
        Question question3 = new Question("KeywordABC");

        question1.setCategory(category);
        question2.setCategory(category);
        question3.setCategory(category);

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        // When
        List<Question> questionList = questionService.getQuestions(Pageable.unpaged(), "AB").getContent();
        List<Question> questionList1 = questionService.getQuestionsFromQuery("AB", Pageable.unpaged()).getContent();

        // Then
        assertThat(questionList).isEqualTo(questionList1)
                .hasSize(2)
                .extracting(Question::getContent)
                .containsExactlyInAnyOrder("KeywordABAA", "KeywordABC");


    }
    @Test
    void shouldReturnHotQuestions() {

        // Given
        questionRepository.deleteAll();
        categoryRepository.save(category);
        Question question1 = new Question("Question11");
        Question question2 = new Question("Question22");
        Question question3 = new Question("Question33");
        Question question4 = new Question("Question44");

        question1.setCategory(category);
        question2.setCategory(category);
        question3.setCategory(category);
        question4.setCategory(category);

        questionRepository.saveAll(List.of(question1, question2, question3, question4));

        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        Answer answer3 = new Answer("Answer3");
        Answer answer4 = new Answer("Answer4");
        Answer answer5 = new Answer("Answer5");
        Answer answer6 = new Answer("Answer6");

        question1.addAnswer(answer1);
        question1.addAnswer(answer5);
        question4.addAnswer(answer2);
        question3.addAnswer(answer3);
        question3.addAnswer(answer4);
        question3.addAnswer(answer6);


        answerRepository.saveAll(List.of(answer1, answer2, answer3, answer4, answer5, answer6));


        // when
        List<Question> questions = questionService.getHotQuestions(Pageable.unpaged()).getContent();

        // Then
        assertThat(questions).extracting(Question::getContent)
                .containsExactly("Question33", "Question11", "Question44", "Question22");

    }

    @Test
    void shouldReturnUnansweredQuestions() {
        // Given
        questionRepository.deleteAll();
        categoryRepository.save(category);
        Question question1 = new Question("Question11");
        Question question2 = new Question("Question22");
        Question question3 = new Question("Question33");
        Question question4 = new Question("Question44");

        question1.setCategory(category);
        question2.setCategory(category);
        question3.setCategory(category);
        question4.setCategory(category);

        questionRepository.saveAll(List.of(question1, question2, question3, question4));

        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        Answer answer3 = new Answer("Answer3");
        Answer answer4 = new Answer("Answer4");
        Answer answer5 = new Answer("Answer5");
        Answer answer6 = new Answer("Answer6");

        question1.addAnswer(answer1);
        question1.addAnswer(answer5);
        question4.addAnswer(answer2);
        question3.addAnswer(answer3);
        question3.addAnswer(answer4);
        question3.addAnswer(answer6);


        answerRepository.saveAll(List.of(answer1, answer2, answer3, answer4, answer5, answer6));


        // when
        List<Question> questions = questionService.getUnansweredQuestions(Pageable.unpaged()).getContent();

        // Then
        assertThat(questions).hasSize(1)
                .extracting(Question::getContent)
                .containsExactly("Question22");
    }

    @Test
    void shouldRemoveQuestion() {
        // Given
        questionRepository.deleteAll();
        categoryRepository.save(category);
        Question question1 = new Question("Question11");
        Question question2 = new Question("Question22");
        Question question3 = new Question("Question33");

        question1.setCategory(category);
        question2.setCategory(category);
        question3.setCategory(category);

        questionRepository.saveAll(List.of(question1, question2, question3));

        // when
        Throwable throwable = catchThrowable(() -> questionService.removeQuestion(question1.getId()));

        // Then
        assertThat(questionRepository.findAll()).hasSize(2)
                .extracting(Question::getContent)
                .containsExactlyInAnyOrder("Question22", "Question33");
    }

    @Test
    void shouldThrowExceptionWhileRemovingNotExistingQuestion() {
        // Given
        questionRepository.deleteAll();
        categoryRepository.save(category);
        Question question1 = new Question("Question11");
        Question question2 = new Question("Question22");
        question1.setCategory(category);
        question2.setCategory(category);

        questionRepository.save(question1);

        // when
       Throwable throwable = catchThrowable(() -> questionService.removeQuestion(question2.getId()));

        // Then
       assertThat(throwable).isNotNull();
    }

    @Test
    void shouldUpdateQuestion() {
        // Given
        questionRepository.deleteAll();
        categoryRepository.saveAndFlush(category);
        Question question1 = new Question("Question11");
        Question question2 = new Question("Question22");
        question1.setCategory(category);
        question2.setCategory(category);

        questionRepository.saveAll(List.of(question1, question2));
        Question newQuestion1 = new Question("Question111");
        Question newQuestion2 = new Question("Question222");
        newQuestion1.setCategory(category);
        newQuestion2.setCategory(category);


        // when
        questionService.updateQuestion(question1.getId(), newQuestion1);
        questionService.updateQuestion(question2.getId(), newQuestion2);

        // Then
        assertThat(questionRepository.findAll()).extracting(Question::getContent)
                .containsExactlyInAnyOrder("Question111", "Question222");
    }

    @Test
    void shouldFindAllByCategoryId() {
        // Given
        questionRepository.deleteAll();
        categoryRepository.save(category);
        Question question1 = new Question("Question11");
        Question question2 = new Question("Question22");
        question1.setCategory(category);
        question2.setCategory(category);

        questionRepository.saveAll(List.of(question1, question2));


        // when
        List<Question> questions = questionService.getQuestionsFromCategory(category.getId(), Pageable.unpaged()).getContent();


        // Then
        assertThat(questions).hasSize(2)
                .extracting(Question::getContent)
                .containsExactlyInAnyOrder("Question11", "Question22");
    }

}