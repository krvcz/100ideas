package pl.sebastian.ideas100.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class StatisticsDTO {

    private long categories;

    private long questions;

    private long answers;



}
