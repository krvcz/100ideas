package pl.sebastian.ideas100.category.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.answers.InvocationInfo;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.common.dto.Message;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryAdminViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private PageImpl<Category> page;


    @BeforeEach
    void setUp() {

         page = new PageImpl<>(
                List.of(new Category("Category1"),
                        new Category("Category2"),
                        new Category("Category3")
                )
        );


        when(categoryService.getCategories(any(), any())).thenReturn(page);

        when(categoryService.getCategories(any())).thenReturn(page);

        when(categoryService.addCategory(any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArgument(0)
        );

    }

    @Test
    void shouldReturnCategoriesView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/categories?keyword=Category"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/category/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("nextPage"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("previousPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("search", "Category"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reverseSort"))
                .andExpect(MockMvcResultMatchers.model().attribute("categoriesPage", page));

    }

    @Test
    void shouldShowSuccessOfAddingCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/categories/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Category4")

                        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"))
                .andExpect(MockMvcResultMatchers.flash().attribute("message", Message.success("Category added!")));

    }

    @Test
    void shouldShowFailOfAddingCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/categories/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/category/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("nextPage"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("previousPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("categoriesPage", page))
                .andExpect(MockMvcResultMatchers.model().hasErrors());
    }

}