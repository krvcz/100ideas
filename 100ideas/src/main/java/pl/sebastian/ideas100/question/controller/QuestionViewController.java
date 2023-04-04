package pl.sebastian.ideas100.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sebastian.ideas100.IdeasConfiguration;
import pl.sebastian.ideas100.common.dto.Message;
import pl.sebastian.ideas100.common.utils.Controller.CommonViewController;
import pl.sebastian.ideas100.question.dto.QuestionStatDto;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.service.AnswerService;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.question.service.QuestionService;

import java.util.UUID;

import static pl.sebastian.ideas100.common.utils.Controller.ControllerUtils.*;


@Controller
@RequestMapping("questions")
@RequiredArgsConstructor
public class QuestionViewController extends CommonViewController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final IdeasConfiguration ideasConfiguration;



    @GetMapping
    public String indexView(){
        return "redirect:/questions/hot";

    }


    @GetMapping("{id}")
    public String singleView(@PathVariable("id") UUID id, Model model){
        model.addAttribute("question", questionService.getQuestion(id));
        model.addAttribute("answers", answerService.getAnswersByQuestionId(id));
        addGlobalAttributes(model);

        return "question/single";
    }

    @GetMapping("add")
    public String addView(@ModelAttribute("question") QuestionStatDto question,
                          Model model){

        addGlobalAttributes(model);

        return "question/add";
    }

    @PostMapping(value ="add")
    public String add(@Valid @ModelAttribute("question") QuestionStatDto questionStatDto,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("question", questionStatDto);
            addGlobalAttributes(model);
            return "question/add";
        }

        Question addedQuestion = questionService.addQuestion(questionStatDto);
        redirectAttributes.addFlashAttribute("success", Message.success("Question added!"));
        return "redirect:/questions/" + addedQuestion.getId();
    }

    @GetMapping("hot")
    public String hotView(Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int page){


        Pageable hotPage = PageRequest.of(page, Integer.parseInt(ideasConfiguration.getPageSize()));
        Page<Question> hotQuestions = questionService.getHotQuestions(hotPage);



        addGlobalAttributes(model, hotPage);
        model.addAttribute("questionsPage", hotQuestions);

        paging(model, hotQuestions);

        return "question/index";
    }


    @GetMapping("unanswered")
    public String unansweredView(Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int page){


        Pageable unansweredPage = PageRequest.of(page, Integer.parseInt(ideasConfiguration.getPageSize()));
        Page<Question> hotQuestions = questionService.getUnansweredQuestions(unansweredPage);

        addGlobalAttributes(model, unansweredPage);

        model.addAttribute("questionsPage", hotQuestions);

        paging(model, hotQuestions);

        return "question/index";
    }




}
