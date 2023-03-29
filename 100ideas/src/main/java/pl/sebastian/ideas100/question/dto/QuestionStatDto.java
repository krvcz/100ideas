package pl.sebastian.ideas100.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class QuestionStatDto {

    private UUID Id;
    private String content;
    private long answers;
    private String category;

}
