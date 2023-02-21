package pl.sebastian.ideas100.question.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.service.QuestionService;


import java.util.UUID;

@Controller
@RequestMapping("/admin/questions")
public class QuestionAdminViewController {

    private final QuestionService questionService;

    private final CategoryService categoryService;

    @Autowired
    public QuestionAdminViewController(QuestionService questionService, CategoryService categoryService) {

        this.questionService = questionService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String questionsView(Model model, @ModelAttribute("question") Question question) {
        model.addAttribute("questions", questionService.getQuestions());
        model.addAttribute("categories", categoryService.getCategories());
//       model.addAttribute("question", new Question());
        return "admin/question/index";
    }

    @PostMapping(value = "add")
    public String add(@ModelAttribute("question") Question question, RedirectAttributes redirectAttributes) {
        questionService.addQuestion(question);
        redirectAttributes.addFlashAttribute("success", "Question added!");

        return "redirect:/admin/questions";
    }

    @PostMapping(value = "update")
    public String edit(@ModelAttribute("question") Question question, RedirectAttributes redirectAttributes) {
        questionService.updateQuestion(question.getId(), question);
        redirectAttributes.addFlashAttribute("success", "Question edited!");
        System.out.println(question.getCategory().getId());
        System.out.println(question.getCategory());
        return "redirect:/admin/questions";
    }

    @PostMapping("/{questionId}/delete")
    public String delete(@ModelAttribute("question") Question question, RedirectAttributes redirectAttributes, @PathVariable UUID questionId) {
        questionService.removeQuestion(questionId);
        redirectAttributes.addFlashAttribute("success", "Question deleted!");
        return "redirect:/admin/questions";
    }



}
