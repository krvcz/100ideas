package pl.sebastian.ideas100.question.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.common.dto.Message;
import pl.sebastian.ideas100.common.utils.SortDirectionEnum;
import pl.sebastian.ideas100.common.utils.controller.CommonViewController;
import pl.sebastian.ideas100.question.dto.QuestionStatDto;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.service.QuestionService;


import java.util.UUID;

import static pl.sebastian.ideas100.common.utils.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/admin/questions")
@RequiredArgsConstructor
public class QuestionAdminViewController extends CommonViewController {

    private final QuestionService questionService;

    private final CategoryService categoryService;


    @GetMapping
    public String questionsView(Model model,
                                @ModelAttribute("question") QuestionStatDto question,
                                @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "field", required = false, defaultValue = "id") String field,
                                @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
                                @RequestParam(value = "keyword", required = false) String search) {


        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);

        SortDirectionEnum reverseSort = null;
        if (SortDirectionEnum.ASC.getReprText().equals(direction)) {
            reverseSort = SortDirectionEnum.DESC;
        } else {
            reverseSort = SortDirectionEnum.ASC;
        }

        Page<Question> questionPage = questionService.getQuestions(pageable, search);

        model.addAttribute("questionsPage", questionPage);
        model.addAttribute("search", search);
        model.addAttribute("reverseSort", reverseSort.getReprText());

        addGlobalAttributes(model, pageable);
        paging(model, questionPage);


        return "admin/question/index";
    }

    @PostMapping(value = "add")
    public String add(@Valid @ModelAttribute("question") QuestionStatDto question,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                      Model model) {

        if (bindingResult.hasErrors()) {
            Pageable pageable = PageRequest.of(page, size);

            Page<Question> questionPage = questionService.getQuestions(pageable, null);

            model.addAttribute("questionsPage", questionPage);
            model.addAttribute("search", null);
            model.addAttribute("reverseSort", "asc");

            addGlobalAttributes(model, pageable);
            paging(model, questionPage);


            return "admin/question/index";
        }

        questionService.addQuestion(question);
        redirectAttributes.addFlashAttribute("message", Message.success("Question added!"));

        return "redirect:/admin/questions";
    }

    @PostMapping(value = "update")
    public String edit(@Valid @ModelAttribute("question") QuestionStatDto questionStatDto,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                      Model model) {

        if (bindingResult.hasErrors()) {
            Pageable pageable = PageRequest.of(page, size);

            Page<Question> questionPage = questionService.getQuestions(pageable, null);

            model.addAttribute("questionsPage", questionPage);
            model.addAttribute("search", null);
            model.addAttribute("reverseSort", "asc");

            addGlobalAttributes(model, pageable);
            paging(model, questionPage);


            return "admin/question/index";
        }


        questionService.updateQuestion(questionStatDto.getId(), questionStatDto);
        redirectAttributes.addFlashAttribute("message", Message.success("Question edited!"));

        return "redirect:/admin/questions";
    }

    @PostMapping("/{questionId}/delete")
    public String delete(@ModelAttribute("question") Question question,
                         RedirectAttributes redirectAttributes,
                         @PathVariable UUID questionId) {
        questionService.removeQuestion(questionId);
        redirectAttributes.addFlashAttribute("message", Message.info("Question deleted!"));

        return "redirect:/admin/questions";
    }

}
