package pl.sebastian.ideas100.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sebastian.ideas100.question.model.Question;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    public List<Question> findAllByCategoryId(UUID categoryId);
}
