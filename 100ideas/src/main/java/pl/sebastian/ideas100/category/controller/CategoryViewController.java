package pl.sebastian.ideas100.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.common.utils.Controller.CommonViewController;
import pl.sebastian.ideas100.question.service.QuestionService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoryViewController extends CommonViewController{

    private final CategoryService categoryService;

    private final QuestionService questionService;


    @GetMapping("{id}")
    public String singleView(@PathVariable("id") UUID categoryId,
                             Model model){
        addGlobalAttributes(model);
        model.addAttribute("category", categoryService.getCategory(categoryId));
        model.addAttribute("questions", questionService.getQuestionsFromCategory(categoryId));

        return "category/single";
    }
}
