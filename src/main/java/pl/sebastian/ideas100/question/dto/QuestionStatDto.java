package pl.sebastian.ideas100.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class QuestionStatDto {

    private UUID id;
    @NotBlank(message = "{ideas.validation.question.constraints.NotNull.message}")
    @Size(min = 10, max=255, message = "{ideas.validation.question.constraints.Size.message}")
    private String content;
    private long answers;
    private String category;

    @NotNull(message = "{ideas.validation.question.constraints.NotNull.message}")
    private UUID categoryId;



}
