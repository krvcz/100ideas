package pl.sebastian.ideas100.category.controller;

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
import pl.sebastian.ideas100.category.dto.CategoryDTO;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.common.dto.Message;
import pl.sebastian.ideas100.common.utils.SortDirectionEnum;
import pl.sebastian.ideas100.common.utils.controller.CommonViewController;


import java.util.UUID;

import static pl.sebastian.ideas100.common.utils.controller.ControllerUtils.*;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminViewController extends CommonViewController {

    private final CategoryService categoryService;

    @GetMapping
    public String categoriesView(Model model,
                                 @ModelAttribute("category") CategoryDTO category,
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

        Page<Category> categoryPage = categoryService.getCategories(pageable, search);

        model.addAttribute("categoriesPage", categoryPage);
        model.addAttribute("search", search);
        model.addAttribute("reverseSort", reverseSort.getReprText());

        addGlobalAttributes(model, pageable);
        paging(model, categoryPage);

        return "admin/category/index";
 }

    @PostMapping(value = "add")
    public String add(@Valid @ModelAttribute("category") CategoryDTO category,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model,
                      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                      @RequestParam(value = "field", required = false, defaultValue = "id") String field,
                      @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction ){

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);

        Page<Category> categoriesPage = categoryService.getCategories(pageable);

        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            model.addAttribute("categoriesPage", categoriesPage);
            addGlobalAttributes(model, pageable);
            paging(model, categoriesPage);
            return "admin/category/index";
        }

        try {
            categoryService.addCategory(category);

        } catch (Exception e){
            model.addAttribute("category", category);
            model.addAttribute("categoriesPage", categoriesPage);
            addGlobalAttributes(model, pageable);
            paging(model, categoriesPage);
            return "admin/category/index";
        }

        redirectAttributes.addFlashAttribute("message", Message.success("Category added!"));

        return "redirect:/admin/categories";
    }

    @PostMapping(value = "update")
    public String edit(@Valid @ModelAttribute("category") CategoryDTO category,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model,
                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                       @RequestParam(value = "field", required = false, defaultValue = "id") String field,
                       @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);
        Page<Category> categoriesPage = categoryService.getCategories(pageable);

        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            model.addAttribute("categoriesPage", categoriesPage);
            addGlobalAttributes(model, pageable);
            paging(model, categoriesPage);
            return "admin/category/index";
        }

        try {
            categoryService.updateCategory(category.getId(), category);

        } catch (Exception e){
            model.addAttribute("category", category);
            model.addAttribute("categoriesPage", categoriesPage);
            addGlobalAttributes(model, pageable);
            paging(model, categoriesPage);
            return "admin/category/index";
        }

        redirectAttributes.addFlashAttribute("message", Message.info("Category edited!"));
        return "redirect:/admin/categories";
    }

    @PostMapping("/{categoryId}/delete")
    public String delete(RedirectAttributes redirectAttributes,
                         @PathVariable UUID categoryId) {
        categoryService.removeCategory(categoryId);
        redirectAttributes.addFlashAttribute("message", Message.success("Category deleted!"));
        return "redirect:/admin/categories";
    }

}
