package pl.sebastian.ideas100.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebastian.ideas100.category.dto.CategoryDTO;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.service.CategoryWithStatsMapper;
import pl.sebastian.ideas100.category.service.CategoryService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> showCategories(Pageable pageable) {

        return new ResponseEntity<>(categoryService.getCategories(pageable), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") UUID id) {

        CategoryDTO category = categoryService.getCategory(id);


        return new ResponseEntity<>(category, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        CategoryDTO categoryAdded = categoryService.addCategory(category);

        return new ResponseEntity<>(categoryAdded, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") UUID id, @RequestBody CategoryDTO category) {
        CategoryDTO categoryFound = categoryService.updateCategory(id, category);

        return new ResponseEntity<>(categoryFound, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeCategory(@PathVariable("id") UUID id) {
        categoryService.removeCategory(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
