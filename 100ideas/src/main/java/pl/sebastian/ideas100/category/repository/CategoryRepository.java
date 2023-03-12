package pl.sebastian.ideas100.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sebastian.ideas100.category.model.Category;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Page<Category> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Category> findById(UUID id, Pageable pageable);
}
