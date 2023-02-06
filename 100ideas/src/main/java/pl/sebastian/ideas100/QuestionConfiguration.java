package pl.sebastian.ideas100;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="questionall")
public class QuestionConfiguration {

    private Integer limit;
    @Value("${helloworld}")
    private Integer helloworld;

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getHelloworld(){
        return helloworld;
    }
}
