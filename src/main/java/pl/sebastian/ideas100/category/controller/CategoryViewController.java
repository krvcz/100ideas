package pl.sebastian.ideas100.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sebastian.ideas100.IdeasConfiguration;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.common.utils.controller.CommonViewController;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.service.QuestionService;

import java.util.UUID;

import static pl.sebastian.ideas100.common.utils.controller.ControllerUtils.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoryViewController extends CommonViewController{

    private final CategoryService categoryService;

    private final QuestionService questionService;

    private final IdeasConfiguration ideasConfiguration;


    @GetMapping("{id}")
    public String singleView(@PathVariable("id") UUID categoryId,
                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                             Model model){

        Pageable pageable = PageRequest.of(page, Integer.parseInt(ideasConfiguration.getPageSize()));
        addGlobalAttributes(model, pageable);

        Page<Question> questionPage = questionService.getQuestionsFromCategory(categoryId, pageable);


        model.addAttribute("category", categoryService.getCategory(categoryId));
        model.addAttribute("questionsPage", questionPage);

        paging(model, questionPage);
        addGlobalAttributes(model, pageable);

        return "category/single";
    }
}
