package pl.sebastian.ideas100.category.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CategoryStatDto {

    private UUID Id;

    private String name;

    private long questions;

    private long answers;




}
