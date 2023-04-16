package pl.sebastian.ideas100;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class IdeasConfiguration {

    @Value("${ideas.pageSize:5}")
    public String pageSize;

}
