package pl.sebastian.ideas100.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.question.model.Question;

import java.util.UUID;

@Controller
@RequestMapping("/admin/categories")
public class CategoryAdminViewController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryAdminViewController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String categoriesView(Model model, @ModelAttribute("category") Category category) {
        model.addAttribute("categories", categoryService.getCategories());
//        model.addAttribute("category", new Category());
        return "admin/category/index";
 }

    @PostMapping(value = "add")
    public String add(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.addCategory(category);
        redirectAttributes.addFlashAttribute("success", "Category added!");

        return "redirect:/admin/categories";
    }

    @PostMapping(value = "update")
    public String edit(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.updateCategory(category.getId(), category);
        redirectAttributes.addFlashAttribute("success", "Category edited!");
        return "redirect:/admin/categories";
    }

    @PostMapping("/{categoryId}/delete")
    public String delete(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes, @PathVariable UUID categoryId) {
        categoryService.removeCategory(categoryId);
        redirectAttributes.addFlashAttribute("success", "Category deleted!");
        return "redirect:/admin/categories";
    }



}
