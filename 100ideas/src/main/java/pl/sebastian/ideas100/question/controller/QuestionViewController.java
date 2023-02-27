package pl.sebastian.ideas100.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.answer.service.AnswerService;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.question.service.QuestionService;

import java.util.UUID;



@Controller
@RequestMapping("questions")
public class QuestionViewController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CategoryService categoryService;


    @Autowired
    public QuestionViewController(QuestionService questionService, AnswerService answerService, CategoryService categoryService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String indexView(Model model){
        model.addAttribute("categories", categoryService.getCategories());

        model.addAttribute("questions", questionService.getQuestions());



        return "question/index";

    }


    @GetMapping("{id}")
    public String singleView(@PathVariable("id") UUID id, Model model){
        model.addAttribute("question", questionService.getQuestion(id));
        model.addAttribute("answers", answerService.getAnswersByQuestionId(id));
        model.addAttribute("categories", categoryService.getCategories());

        return "question/single";
    }

    @GetMapping("add")
    public String addView(Model model){
        model.addAttribute("question", new Question());
        model.addAttribute("categories", categoryService.getCategories());

        return "question/add";
    }

    @PostMapping(value ="add")
    public String add(@ModelAttribute("question") Question question, RedirectAttributes redirectAttributes) {
        questionService.addQuestion(question);
        redirectAttributes.addFlashAttribute("success", "Question added!");

        return "redirect:/questions/add";
    }



}
