package pl.sebastian.ideas100.common.utils.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sebastian.ideas100.IdeasConfiguration;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.service.QuestionService;

import static pl.sebastian.ideas100.common.utils.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchViewController {

    private final QuestionService questionService;

    private final IdeasConfiguration ideasConfiguration;

    private final CategoryService categoryService;

    @GetMapping
    public String searchView(Model model,
                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                             @RequestParam(value = "query", required = false) String query) {

        Pageable pageable = PageRequest.of(page, Integer.parseInt(ideasConfiguration.getPageSize()));
        int nextPage = pageable.next().getPageNumber();
        int previousPage = pageable.previousOrFirst().getPageNumber();

        if (query != null) {
            Page<Question> searchedQuestions = questionService.getQuestionsFromQuery(query, pageable);
            model.addAttribute("query", query);
            model.addAttribute("searchedElements", searchedQuestions);
            model.addAttribute("nextPage", nextPage);
            model.addAttribute("previousPage", previousPage);
            paging(model, searchedQuestions);
        }

        model.addAttribute("categories", categoryService.getCategories(Pageable.unpaged()));

        return "search/index";
    }

}
