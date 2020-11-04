package br.com.rodrigo.aprendigame.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Quizz implements Serializable {

    private Long id;
    private String code;
    private String title;
    private CourseClass courseClass;
    private String amountOfQuestions;
    private List<Question> questions;
    private Double points;

}
