package pl.sebastian.ideas;

import pl.sebastian.ideas.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IdeasApplication {

    public static void main(String[] args) {
        IdeasApplication.start();
    }

    private static void start() {
        Scanner scanner = new Scanner(System.in);
        List<Question> questionsList = new ArrayList<>();
        String action;
        String questionText;


        while (true) {
            System.out.println("What would you like to do:");
            System.out.println("Hint: Use lower brackets only");
            action = scanner.nextLine();
            if (action.equals("add new question")) {
                System.out.println("Type your question:");
                questionText = scanner.nextLine();
                questionsList.add(new Question(questionText));
            }
            if (action.equals("list questions")) {
                System.out.println("Type your question:");
                questionText = scanner.nextLine();
                questionsList.add(new Question(questionText));
            }
            if (action.equals("quit")) {
                break;
            }


        }


    }

}
