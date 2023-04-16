package pl.sebastian.ideas100.question.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AnswerStatDto {

    private UUID id;

    private String content;

    private String question;

    private UUID questionId;

}
