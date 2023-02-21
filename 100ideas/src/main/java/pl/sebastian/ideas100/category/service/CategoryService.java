package pl.sebastian.ideas100.service;

import org.springframework.stereotype.Service;
import pl.sebastian.ideas100.domain.model.Category;
import pl.sebastian.ideas100.exception.NoContentException;

import java.util.*;
@Service
public class CategoryService {
    private List<Category> categoryMock = new ArrayList<>(Arrays.asList(
            new Category("życie"),
            new Category("sport"),
            new Category("polityka"),
            new Category("jedzenie"),
            new Category("muzyka")));

    public List<Category> getCategories() {
        if (categoryMock.isEmpty()) {
            throw new NoContentException();
        }
        return categoryMock;
    }

    public Optional<Category> getCategory(UUID id) {
        return getCategories().stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    public Category updateCategory(UUID id, Category category) {
        Optional<Category> categoryOld = getCategory(id);
        if (categoryOld.isPresent()) {
            categoryMock.remove(categoryOld.get());
            categoryMock.add(category);
        } else {
            categoryMock.add(category);
        }
        category.setId(id);

        return category;

    }

    public void removeCategory(UUID id) {
        Optional<Category> categoryOld = getCategory(id);

        if (categoryOld.isPresent()) {
            categoryMock.remove(categoryOld.get());
        } else {
            throw new NoContentException();
        }
    }

    public Category addCategory(Category category) {
        categoryMock.add(category);

        return category;
    }
}
