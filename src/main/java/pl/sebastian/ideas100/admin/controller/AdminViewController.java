package pl.sebastian.ideas100.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sebastian.ideas100.category.service.CategoryService;

@Controller
@RequestMapping( "/admin")
@RequiredArgsConstructor
public class AdminViewController {

    private final CategoryService categoryService;

    @GetMapping
    public String indexView(Model model){

        model.addAttribute("Statistics", categoryService.getAllStatistics());

        return "admin/index";
    }

}
