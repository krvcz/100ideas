package pl.sebastian.ideas100.common.utils.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sebastian.ideas100.category.dto.CategoryStatDto;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.service.QuestionService;

import java.util.List;
import java.util.UUID;

import static pl.sebastian.ideas100.common.utils.Controller.ControllerUtils.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexViewController extends CommonViewController {

    private final CategoryService categoryService;

    private final QuestionService questionService;



    @GetMapping
    public String mainPageView(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CategoryStatDto> categoriesWithStatsDto = categoryService.getCategoriesWithStats(pageable);

        model.addAttribute("categoriesStats", categoriesWithStatsDto);

        addGlobalAttributes(model, pageable);
        paging(model, categoriesWithStatsDto);

        return "index";

    }

    // TODO zmiana na DTO
    public List<Question> topQuestionsByCategory(UUID categoryId) {
        List<Question> topQuestions = questionService.getHotQuestionsFromCategory(categoryId, 2);
        return topQuestions;
    }

}
