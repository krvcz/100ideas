package pl.sebastian.ideas100.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.question.model.Question;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    Page<Question> findAllByCategoryId(UUID categoryId, Pageable pageable);

    @Query("from Question q order by size(q.answers) desc ")
    Page<Question> findHot(Pageable pageable);

    @Query("from Question q where size(q.answers) = 0")
    Page<Question> getUnansweredQuestions(Pageable unansweredPage);

    @Query("from Question q where upper(q.category.name) like concat('%',upper(:query),'%') or upper(q.content) like concat('%',upper(:query),'%')")
    Page<Question> findAllByQuery(@Param("query") String query, Pageable pageable);



}

