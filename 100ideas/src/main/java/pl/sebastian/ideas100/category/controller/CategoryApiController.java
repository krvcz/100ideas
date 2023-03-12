package pl.sebastian.ideas100.category.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.exception.NoContentException;
import pl.sebastian.ideas100.category.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<Category>> showCategories(Pageable pageable) {

        return new ResponseEntity<>(categoryService.getCategories(pageable), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") UUID id) {

        Optional<Category> category = categoryService.getCategory(id);

        return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseThrow(NoContentException::new);

    }
    @PostMapping("{id}")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category categoryAdded = categoryService.addCategory(category);

        return new ResponseEntity<>(categoryAdded, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<Category> addCategory(@PathVariable("id") UUID id, @RequestBody Category category) {
        Category categoryFound = categoryService.updateCategory(id, category);

        return new ResponseEntity<>(categoryFound, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeCategory(@PathVariable("id") UUID id) {
        categoryService.removeCategory(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
