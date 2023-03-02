package pl.sebastian.ideas100.category.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.common.dto.Message;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/categories")
public class CategoryAdminViewController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryAdminViewController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String categoriesView(Model model,
                                 @ModelAttribute("category") Category category,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(value = "field", required = false, defaultValue = "id") String field,
                                 @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
                                 @RequestParam(value = "keyword", required = false) String search) {


        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);
        Integer nextPage = pageable.next().getPageNumber();
        Integer previousPage = pageable.previousOrFirst().getPageNumber();

        String reverseSort = null;
        if ("asc".equals(direction)) {
            reverseSort = "desc";
        } else {
            reverseSort = "asc";
        }


        Page<Category> categoryPage = categoryService.getCategories(pageable, search);


        model.addAttribute("categoriesPage", categoryPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("search", search);
        model.addAttribute("reverseSort", reverseSort);

        paging(model, categoryPage);
        return "admin/category/index";
 }

    @PostMapping(value = "add")
    public String add(@Valid @ModelAttribute("category") Category category,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model,
                      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                      @RequestParam(value = "field", required = false, defaultValue = "id") String field,
                      @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction ){


        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);
        Integer nextPage = pageable.next().getPageNumber();
        Integer previousPage = pageable.previousOrFirst().getPageNumber();
        Page<Category> categoriesPage = categoryService.getCategories(pageable);


        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            model.addAttribute("categoriesPage", categoriesPage);
            model.addAttribute("nextPage", nextPage);
            model.addAttribute("previousPage", previousPage);
            paging(model, categoriesPage);
            return "admin/category/index";
        }

        try {
            categoryService.addCategory(category);

        } catch (Exception e){
            model.addAttribute("category", category);
            model.addAttribute("categoriesPage", categoriesPage);
            model.addAttribute("nextPage", nextPage);
            model.addAttribute("previousPage", previousPage);
            paging(model, categoriesPage);
            return "admin/category/index";
        }

        redirectAttributes.addFlashAttribute("message", Message.success("Category added!"));

        return "redirect:/admin/categories";
    }

    @PostMapping(value = "update")
    public String edit(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.updateCategory(category.getId(), category);
        redirectAttributes.addFlashAttribute("message", Message.info("Category edited!"));
        return "redirect:/admin/categories";
    }

    @PostMapping("/{categoryId}/delete")
    public String delete(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes, @PathVariable UUID categoryId) {
        categoryService.removeCategory(categoryId);
        redirectAttributes.addFlashAttribute("message", Message.success("Category deleted!"));
        return "redirect:/admin/categories";
    }

    public void paging(Model model, Page page){
        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }


}
