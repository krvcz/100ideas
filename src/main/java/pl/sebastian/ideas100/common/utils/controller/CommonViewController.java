package pl.sebastian.ideas100.common.utils.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import pl.sebastian.ideas100.category.service.CategoryService;


public abstract class CommonViewController {

    @Autowired
    protected  CategoryService categoryService;

    public void addGlobalAttributes(Model model) {
        model.addAttribute("categories", categoryService.getCategories(Pageable.unpaged()));
    }

    public void addGlobalAttributes(Model model, Pageable pageable) {
        model.addAttribute("categories", categoryService.getCategories(Pageable.unpaged()));
        Integer nextPage = pageable.next().getPageNumber();
        Pageable previousPage = pageable.previousOrFirst();
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("previousPage", previousPage.getPageNumber());
        model.addAttribute("hasPreviousPage", previousPage.hasPrevious());
    }

}
