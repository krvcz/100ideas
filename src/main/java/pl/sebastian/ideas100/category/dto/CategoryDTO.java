package pl.sebastian.ideas100.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private UUID Id;

    @NotBlank(message = "{ideas.validation.category.constraints.NotBlank.message}")
    @Size(min = 5, max = 255, message = "{ideas.validation.category.constraints.Size.message}")
    private String name;

    private long questions;

    private long answers;

    public CategoryDTO(String name) {
        this.name = name;
    }

}
