package pl.sebastian.ideas100.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.common.dto.StatisticsDTO;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Page<Category> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT DISTINCT new pl.sebastian.ideas100.common.dto.StatisticsDTO(count( distinct c), count( distinct q), count( distinct a) ) " +
            "FROM Answer a " +
            "FULL JOIN Question q ON a.question.id = q.id " +
            "FULL JOIN Category c ON c.id = q.category.id")
    StatisticsDTO generateStats();

}
