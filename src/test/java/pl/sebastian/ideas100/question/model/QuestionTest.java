package pl.sebastian.ideas100.question.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class QuestionTest {

    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question("Jak żyć ?");
    }

    @Test
    void shouldReturnNull() {
        // When
        Set<Answer> answers = question.getAnswers();

        // Then
        assertThat(answers).isNull();

    }

    @Test
    void shouldReturnSetContainOneAnswer() {
        // Given
        Answer answer = new Answer("Szybko");

        // When
        question.addAnswer(answer);
        Set<Answer> answers = question.getAnswers();

        // Then
        assertThat(answers).isNotNull();
        assertThat(answers).containsExactly(answer);

    }

    @Test
    void shouldReturnTheSameQuestion() {
        // Given
        Answer firstAnswer = new Answer("Szybko");
        Answer secondAnswer = new Answer("Wolno");

        // When
        question.addAnswer(firstAnswer);
        question.addAnswer(secondAnswer);
        Question question1 = firstAnswer.getQuestion();
        Question question2 = secondAnswer.getQuestion();
        Set<Answer> answers = question.getAnswers();

        // Then
        assertThat(question1).isEqualTo(question2);
        assertThat(firstAnswer).isIn(answers);
        assertThat(secondAnswer).isIn(answers);

    }

}