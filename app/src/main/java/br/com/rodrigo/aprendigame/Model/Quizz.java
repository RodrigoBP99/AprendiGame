package br.com.rodrigo.aprendigame.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Quizz implements Serializable {

    private Long id;
    private String title;
    private Teacher teacher;
    private CoursesUnit courseUnit;
    private String amountOfQuestions;
    private List<Question> questions;

}
