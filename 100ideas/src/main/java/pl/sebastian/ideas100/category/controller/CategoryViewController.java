package pl.sebastian.ideas100.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.question.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("categories")
public class CategoryViewController {

    private final CategoryService categoryService;

    private final QuestionService questionService;
    @Autowired
    public CategoryViewController(CategoryService categoryService, QuestionService questionService) {
        this.categoryService = categoryService;
        this.questionService = questionService;
    }

    @GetMapping("{id}")
    public String singleView(@PathVariable("id") UUID categoryId, Model model){
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("category", categoryService.getCategory(categoryId));
        model.addAttribute("questions", questionService.getQuestionsFromCategory(categoryId));

        return "category/single";
    }
}
