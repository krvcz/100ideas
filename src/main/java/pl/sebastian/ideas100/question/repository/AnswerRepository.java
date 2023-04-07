package pl.sebastian.ideas100.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sebastian.ideas100.question.model.Answer;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {

    public List<Answer> findAllByQuestionId(UUID questionId);
}
