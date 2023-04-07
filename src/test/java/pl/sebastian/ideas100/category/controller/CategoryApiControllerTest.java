package pl.sebastian.ideas100.category.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sebastian.ideas100.category.dto.CategoryDTO;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.category.service.CategoryService;
import pl.sebastian.ideas100.category.service.CategoryWithStatsMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryApiControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public CategoryService categoryService;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public CategoryWithStatsMapper categoryWithStatsMapper;

    private PageImpl<Category> page;

    private CategoryDTO category;

    @BeforeEach
    void setUp() {

        page = new PageImpl<>(
                List.of(new Category("Category1"),
                        new Category("Category2"),
                        new Category("Category3")
                )
        );

        category =  categoryWithStatsMapper.map(page.getContent().get(0));


        when(categoryService.getCategories(any(), any())).thenReturn(page);

        when(categoryService.getCategories(any())).thenReturn(page);

        when(categoryService.getCategory(any())).thenReturn(categoryWithStatsMapper.map(page.getContent().get(0)));

        when(categoryService.addCategory(any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArgument(0)

        );

        when(categoryService.updateCategory(any(), any())).thenAnswer(
                (InvocationOnMock invocationOnMock) -> invocationOnMock.getArgument(1)
        );

    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(page)));

    }

    @Test
    void shouldReturnCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}",
                        category.getId())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(category)))
        ;
    }

    @Test
    void shouldAddCategory() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(category)));
    }

    @Test
    void shouldUpdateCategory() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(category)));
    }

    @Test
    void shouldRemoveCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());




    }
}