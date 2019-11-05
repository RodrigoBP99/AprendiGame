package br.com.rodrigo.aprendigame.Model;

import lombok.Data;

@Data
public class Quizz {

    private Long id;
    private String title;
    private Teacher teacher;
    private CoursesUnit courseUnit;
    private String amountOfQuestions;

}
