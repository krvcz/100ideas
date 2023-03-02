package pl.sebastian.ideas100.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {

    private String content;
    private String msg;

    private String backgroundAlert;

    public static Message info(String msg){
        return new Message("info", msg, "bg-info");
    }

    public static Message error(String msg){
        return new Message("error", msg, "bg-maroon");
    }

    public static Message success(String msg){
        return new Message("success", msg, "bg-success");
    }

}


